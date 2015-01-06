package pe.apiconz.android.cooltura.app;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import pe.apiconz.android.cooltura.app.data.PlaceContract;
import pe.apiconz.android.cooltura.app.utils.Utility;

import static pe.apiconz.android.cooltura.app.data.PlaceContract.PlaceEntry;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String PLACE_KEY = "placeId";
    private static final String LOG_TAG = PlaceDetailFragment.class.getSimpleName();
    private static final String PLACE_SHARE_HASHTAG = " #CoolturaApp";
    private ShareActionProvider mShareActionProvider;
    private static final int DETAIL_PLACE_LOADER = 0;

    private String mPlace;

    private static final String[] PLACE_DETAIL_COLUMNS = {
            PlaceEntry.TABLE_NAME + "." + PlaceEntry._ID,
            PlaceEntry.COLUMN_PLACE_NAME,
            PlaceEntry.COLUMN_PLACE_ADDRESS,
            PlaceContract.TypeEntry.COLUMN_TYPE_NAME,
            PlaceContract.LocationEntry.COLUMN_CITY_NAME,
            PlaceContract.LocationEntry.COLUMN_COORD_LAT,
            PlaceContract.LocationEntry.COLUMN_COORD_LONG
    };

    private TextView mTxtPlaceDetailName, mTxtPlaceDetailAddress, mTxtPlaceDetailTypeName, mTxtPlaceDetailCityName;
    private ImageView mImgPlace, mImgPlaceType;

    public PlaceDetailFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(PLACE_KEY, mPlace);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.v(LOG_TAG, "in onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mPlace = savedInstanceState.getString(PLACE_KEY);
        }

        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey(PLACE_KEY)) {
            getLoaderManager().initLoader(DETAIL_PLACE_LOADER, null, this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey(PLACE_KEY) && mPlace != null) {
            getLoaderManager().restartLoader(DETAIL_PLACE_LOADER, null, this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_place_detail, container, false);

        mTxtPlaceDetailName = ((TextView) rootView.findViewById(R.id.txtPlaceDetailName));
        mTxtPlaceDetailAddress = ((TextView) rootView.findViewById(R.id.txtPlaceDetailAddress));
        mTxtPlaceDetailTypeName = ((TextView) rootView.findViewById(R.id.txtPlaceDetailTypeName));
        mTxtPlaceDetailCityName = ((TextView) rootView.findViewById(R.id.txtPlaceDetailCityName));

        mImgPlace = (ImageView) rootView.findViewById(R.id.imgPlace);
        mImgPlaceType = (ImageView) rootView.findViewById(R.id.imgPlaceType);

        return rootView;


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.v(LOG_TAG, "in onCreateOptionsMenu");
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.detailfragment, menu);

        // Retrieve the share menu item
        MenuItem menuItem = menu.findItem(R.id.action_share);

        // Get the provider and hold onto it to set/change the share intent.
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        // If onLoadFinished happens before this, we can go ahead and set the share intent now.
        if (mPlace != null) {
            mShareActionProvider.setShareIntent(createSharePlaceIntent());
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.v(LOG_TAG, "In onCreateLoader");

        long placeId = getArguments().getLong(PLACE_KEY);

        Log.v(LOG_TAG, "Place ID:" + placeId);

        if (placeId == -1) {
            return null;
        }

        Uri placeByIdUri = PlaceEntry.buildPlaceUri(placeId);

        Log.v(LOG_TAG, placeByIdUri.toString());

        return new CursorLoader(
                getActivity(),
                placeByIdUri,
                PLACE_DETAIL_COLUMNS,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.v(LOG_TAG, "In onLoadFinished");
        if (!data.moveToFirst()) {
            Log.d(LOG_TAG, "No returned values");
            return;
        }

        String placeName = data.getString(data.getColumnIndex(PlaceEntry.COLUMN_PLACE_NAME));
        mTxtPlaceDetailName.setText(placeName);

        String placeAddress = data.getString(data.getColumnIndex(PlaceEntry.COLUMN_PLACE_ADDRESS));
        mTxtPlaceDetailAddress.setText(placeAddress);

        String placeTypeName = data.getString(data.getColumnIndex(PlaceContract.TypeEntry.COLUMN_TYPE_NAME));
        mTxtPlaceDetailTypeName.setText(placeTypeName);

        String placeCityName = data.getString(data.getColumnIndex(PlaceContract.LocationEntry.COLUMN_CITY_NAME));
        mTxtPlaceDetailCityName.setText(placeCityName);

        mImgPlace.setImageResource(R.drawable.ic_museum);
        mImgPlaceType.setImageResource(Utility.getIconResourceForPlaceType(placeTypeName));

        // We still need this for the share intent
        mPlace = String.format("%s - %s - %s - %s", placeName, placeAddress, placeTypeName, placeCityName);

        Log.v(LOG_TAG, "Place String: " + mPlace);

        // If onCreateOptionsMenu has already happened, we need to update the share intent now.
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(createSharePlaceIntent());
        }
    }


    private Intent createSharePlaceIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(PLACE_KEY, mPlace + PLACE_SHARE_HASHTAG);
        return shareIntent;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
