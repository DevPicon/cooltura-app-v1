package pe.apiconz.android.cooltura.app;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pe.apiconz.android.cooltura.app.data.PlaceContract;
import pe.apiconz.android.cooltura.app.utils.Constants;

/**
 * Created by Indra on 05/01/2015.
 */
public class FetchPlacesTask extends AsyncTask<String, Void, Void> {

    private final String LOG_TAG = FetchPlacesTask.class.getSimpleName();
    private final Context mContext;
    private List<String> stringArray = new ArrayList<>();

    public FetchPlacesTask(Context context) {
        super();
        Log.d(LOG_TAG, "Entro a FetchPlacesTask()");
        mContext = context;
    }

    @Override
    public Void doInBackground(final String... params) {

        Log.d(LOG_TAG, "Entro a doInBackground()");

        try {
            // Temporarily I use Firebase datasource
            Firebase.setAndroidContext(mContext);
            Firebase myFirebaseRef = new Firebase("https://cooltura-app.firebaseio.com/");
            myFirebaseRef.child("places").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                            getPlacesStringFromJson(dataSnapshot);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

        } catch (Exception e) {
            Log.e(LOG_TAG, "Surgio un error", e);
        }

        return null;
    }

    private List<String> getPlacesStringFromJson(DataSnapshot dataSnapshot) throws JSONException {
        ArrayList<HashMap<String, Object>> values = (ArrayList<HashMap<String, Object>>) dataSnapshot.getValue();
        //Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();
        Log.i(LOG_TAG, "Size:" + values.size());
        Log.d(LOG_TAG, values.toString());

        List<String> places = new ArrayList<>();
        for (HashMap<String, Object> placeMap : values) {
            if (placeMap != null) {
                double lat = Double.valueOf(String.valueOf(placeMap.get(Constants.LATITUDE)));
                double lon = Double.valueOf(String.valueOf(placeMap.get(Constants.LONGITUDE)));
                String cityName = String.valueOf(placeMap.get(Constants.CITY));

                long cityId = addLocation(cityName, lat, lon);

                String placeType = String.valueOf(placeMap.get(Constants.PLACE_TYPE));

                long placeTypeId = addPlaceType(placeType);

                String placeName = String.valueOf(placeMap.get(Constants.NAME));
                String placeAddress = String.valueOf(placeMap.get(Constants.ADDRESS));

                long placeId = addPlace(placeName, placeAddress, cityId, placeTypeId);

                Log.d(LOG_TAG, "The place " + placeName + " has been inserted with ID: " + placeId);


            }


        }

       /* for (Iterator<String> iterator = value.iterator(); iterator.hasNext(); ) {
            Map<String, Object> placeMap = (Map<String, Object>) value.get(iterator.next());
            Log.i(LOG_TAG, "PlaceMap Size:" + placeMap.size());
            String place = "";
            place += String.valueOf(placeMap.get(Constants.NAME));
            place += ("|" + String.valueOf(placeMap.get(Constants.DISTRICT)));
            place += ("|" + String.valueOf(placeMap.get(Constants.INFO)));
            places.add(place);
        } */

        for (String s : places) {
            Log.v(LOG_TAG, "Place entry: " + s);
        }

        return places;

    }

    private long addPlace(String placeName, String placeAddress, long locationId, long placeTypeId) {
        Log.v(LOG_TAG, "addPlace()");

        Log.v(LOG_TAG, "Inserting " + placeName + " with address: " + placeAddress + ", location id:" + locationId + " and type id:" + placeTypeId);

        Cursor cursor = mContext.getContentResolver().query(
                PlaceContract.PlaceEntry.CONTENT_URI,
                new String[]{PlaceContract.PlaceEntry._ID},
                PlaceContract.PlaceEntry.COLUMN_PLACE_NAME + " = ? ",
                new String[]{placeName},
                null);

        if (cursor.moveToFirst()) {
            Log.v(LOG_TAG, "'" + placeName + "' found in database");
            int placeIdIndex = cursor.getColumnIndex(PlaceContract.PlaceEntry._ID);
            return cursor.getLong(placeIdIndex);
        } else {
            Log.v(LOG_TAG, "Didn't find it in database, inserting now!");
            ContentValues placeValues = new ContentValues();
            placeValues.put(PlaceContract.PlaceEntry.COLUMN_PLACE_NAME, placeName);
            placeValues.put(PlaceContract.PlaceEntry.COLUMN_PLACE_ADDRESS, placeAddress);
            placeValues.put(PlaceContract.PlaceEntry.COLUMN_LOCATION_KEY, locationId);
            placeValues.put(PlaceContract.PlaceEntry.COLUMN_TYPE_KEY, placeTypeId);

            Uri placeInsertUri = mContext.getContentResolver()
                    .insert(PlaceContract.PlaceEntry.CONTENT_URI, placeValues);

            return ContentUris.parseId(placeInsertUri);
        }
    }

    private long addLocation(String cityName, double lat, double lon) {
        Log.v(LOG_TAG, "Inserting " + cityName + " with coord: " + lat + "," + lon);

        Cursor cursor = mContext.getContentResolver().query(
                PlaceContract.LocationEntry.CONTENT_URI,
                new String[]{PlaceContract.LocationEntry._ID},
                PlaceContract.LocationEntry.COLUMN_CITY_NAME + " = ? ",
                new String[]{cityName},
                null);

        if (cursor.moveToFirst()) {
            Log.v(LOG_TAG, "Found it in database");
            int locationIdIndex = cursor.getColumnIndex(PlaceContract.LocationEntry._ID);
            return cursor.getLong(locationIdIndex);
        } else {
            Log.v(LOG_TAG, "Didn't find it in database, inserting now!");
            ContentValues values = new ContentValues();
            values.put(PlaceContract.LocationEntry.COLUMN_CITY_NAME, cityName);
            values.put(PlaceContract.LocationEntry.COLUMN_COORD_LAT, lat);
            values.put(PlaceContract.LocationEntry.COLUMN_COORD_LONG, lon);

            Uri locationInsertUri = mContext.getContentResolver()
                    .insert(PlaceContract.LocationEntry.CONTENT_URI, values);

            return ContentUris.parseId(locationInsertUri);
        }
    }


    private long addPlaceType(String placeTypeName) {
        Log.v(LOG_TAG, "Inserting " + placeTypeName + ".");

        Cursor cursor = mContext.getContentResolver().query(
                PlaceContract.TypeEntry.CONTENT_URI,
                new String[]{PlaceContract.TypeEntry._ID},
                PlaceContract.TypeEntry.COLUMN_TYPE_NAME + " = ? ",
                new String[]{placeTypeName},
                null);

        if (cursor.moveToFirst()) {
            Log.v(LOG_TAG, "Found it in database");
            int placeTypeIdIndex = cursor.getColumnIndex(PlaceContract.TypeEntry._ID);
            return cursor.getLong(placeTypeIdIndex);
        } else {
            Log.v(LOG_TAG, "Didn't find it in database, inserting now!");
            ContentValues placeTypeValues = new ContentValues();
            placeTypeValues.put(PlaceContract.TypeEntry.COLUMN_TYPE_NAME, placeTypeName);

            Uri placeTypeInsertUri = mContext.getContentResolver()
                    .insert(PlaceContract.TypeEntry.CONTENT_URI, placeTypeValues);

            return ContentUris.parseId(placeTypeInsertUri);
        }
    }

}
