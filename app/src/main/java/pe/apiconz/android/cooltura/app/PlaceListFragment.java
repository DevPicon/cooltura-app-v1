package pe.apiconz.android.cooltura.app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import pe.apiconz.android.cooltura.app.data.PlaceContract;
import pe.apiconz.android.cooltura.app.service.PlaceService;

public class PlaceListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String SELECTED_KEY = "selectedKey";
    private String mCityName;
    private static final int PLACE_LOADER = 0;

    private static final String[] PLACES_COLUMNS = {
            PlaceContract.PlaceEntry.TABLE_NAME + "." + PlaceContract.PlaceEntry._ID,
            PlaceContract.PlaceEntry.COLUMN_PLACE_NAME,
            PlaceContract.TypeEntry.COLUMN_TYPE_NAME,
            PlaceContract.LocationEntry.COLUMN_CITY_NAME
    };

    public static final int COL_PLACE_ID = 0;
    public static final int COL_PLACE_NAME = 1;
    public static final int COL_TYPE_NAME = 2;
    public static final int COL_CITY_NAME = 3;

    private PlaceAdapter mPlacesAdapter;
    private int mPosition;
    private ListView mListView;

    private final String LOG_TAG = PlaceListFragment.class.getSimpleName();

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.v(LOG_TAG, "Invoke refresh option");

        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            //TODO: Temporarily I send country code to get places list
            updatePlaces();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mPosition != ListView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        Log.d(LOG_TAG, "Entro a onStart()");
        super.onStart();
        updatePlaces();
    }

    @Override
    public void onResume() {
        Log.d(LOG_TAG, "Entro a onResume()");
        super.onResume();
        if (mCityName != null) {
            getLoaderManager().restartLoader(PLACE_LOADER, null, this);
        }
    }

    private void updatePlaces() {
        Log.d(LOG_TAG, "Entro a updatePlaces()");
        Intent alarmIntent = new Intent(getActivity(), PlaceService.AlarmReceiver.class);
        alarmIntent.putExtra(PlaceService.CITY_QUERY_EXTRA, "Lima");

        PendingIntent pi = PendingIntent.getBroadcast(getActivity(), 0, alarmIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager am=(AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);

        //Set the AlarmManager to wake up the system.
        am.set(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis() + 5000, pi);

        /*
        //TODO: Add RECEIVE_BOOT_COMPLETED permission on AndroidManifest, evaluate the intent and declare intent-filter
        //https://developer.android.com/training/scheduling/alarms.html
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 18);

        //Set the AlarmManager to wake up the system.
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pi);*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mPlacesAdapter = new PlaceAdapter(
                getActivity(), null, 0
        );

        mListView = (ListView) rootView.findViewById(R.id.listview_museums);
        mListView.setAdapter(mPlacesAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor cursor = mPlacesAdapter.getCursor();

                if (null != cursor && cursor.moveToPosition(position)) {
                    long placeId = cursor.getLong(COL_PLACE_ID);
                    ((Callback) getActivity()).onItemSelected(placeId);
                }

                mPosition = position;
            }
        });

        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }

        return rootView;
    }

    public interface Callback {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(long placeId);
    }

    public PlaceListFragment() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(PLACE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String sortOrder = PlaceContract.PlaceEntry.COLUMN_PLACE_NAME + " ASC";
        mCityName = "Lima";
        Uri placesByCityNameUri = PlaceContract.PlaceEntry.buildPlacesByCityName(mCityName);
        Log.d(LOG_TAG, "Uri:" + placesByCityNameUri.toString());

        return new CursorLoader(getActivity(),
                placesByCityNameUri,
                PLACES_COLUMNS,
                null,
                null,
                sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mPlacesAdapter.swapCursor(data);
        if (mPosition != ListView.INVALID_POSITION) {
            mListView.setSelection(mPosition);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mPlacesAdapter.swapCursor(null);
    }
}
