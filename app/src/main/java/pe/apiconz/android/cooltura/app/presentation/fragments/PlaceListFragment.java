package pe.apiconz.android.cooltura.app.presentation.fragments;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
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

import butterknife.Bind;
import pe.apiconz.android.cooltura.app.R;
import pe.apiconz.android.cooltura.app.model.data.PlaceContract;
import pe.apiconz.android.cooltura.app.presentation.adapters.PlaceAdapter;

public class PlaceListFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor> {


    @Nullable
    @Bind(R.id.listview_places)
    ListView mListView;


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

    private final String LOG_TAG = PlaceListFragment.class.getSimpleName();

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.placelistfragment, menu);
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
}

                @Override
                public View onCreateView(LayoutInflater inflater, ViewGroup container,
                        Bundle savedInstanceState) {
                    View rootView = super.onCreateView(inflater,container,savedInstanceState);

                    mPlacesAdapter = new PlaceAdapter(
                            getActivity(), null, 0
                    );

                    mListView.setEmptyView(rootView.findViewById(android.R.id.empty));
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

    @Override
    protected int getFragmentLayoutResourceId() {
        return R.layout.fragment_main;
    }

    public interface Callback {
        /**
         * Callback for when an item has been selected.
         */
        void onItemSelected(long placeId);
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

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mCityName = prefs.getString("pref_locationCity", getString(R.string.pref_location_default));

        Log.d(LOG_TAG, "mCityName =" + mCityName);

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
