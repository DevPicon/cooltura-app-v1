package pe.apiconz.android.cooltura.app.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pe.apiconz.android.cooltura.app.R;
import pe.apiconz.android.cooltura.app.data.PlaceContract;
import pe.apiconz.android.cooltura.app.utils.Constants;

/**
 * Created by Indra on 06/01/2015.
 */
public class PlaceSyncAdapter extends AbstractThreadedSyncAdapter {
    public final String LOG_TAG = PlaceSyncAdapter.class.getCanonicalName();
    // Interval at which to sync with the weather, in milliseconds.
    // 60 seconds (1 minute) * 180 = 3 hours
    public static final int SYNC_INTERVAL = 60 * 180;
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;

    public PlaceSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.v(LOG_TAG, "onPerformSync()");
        Log.d(LOG_TAG, "onHandleIntent()");

        //TODO: Make data load only for a specific city

        //String cityQuery = intent.getStringExtra(CITY_QUERY_EXTRA);

        Log.d(LOG_TAG, "Entro a doInBackground()");

        try {
            // Temporarily I use Firebase datasource
            Firebase.setAndroidContext(getContext());
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
    }

    /**
     * Helper method to have the sync adapter sync immediately
     *
     * @param context The context used to access the account service
     */
    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);
    }

    /**
     * Helper method to get the fake account to be used with SyncAdapter, or make a new one
     * if the fake account doesn't exist yet.  If we make a new account, we call the
     * onAccountCreated method so we can initialize things.
     *
     * @param context The context used to access the account service
     * @return a fake account.
     */
    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if (null == accountManager.getPassword(newAccount)) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */
            onAccountCreated(newAccount, context);

        }
        return newAccount;
    }


    private static void onAccountCreated(Account newAccount, Context context) {
        /*
         * Since we've created an account
         */
        PlaceSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);

        /*
         * Without calling setSyncAutomatically, our periodic sync will not be enabled.
         */
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);

        /*
         * Finally, let's do a sync to get things started
         */
        syncImmediately(context);
    }

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }


    /**
     * Helper method to schedule the sync adapter periodic execution
     */
    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, new Bundle(), syncInterval);
        }
    }

    private List<String> getPlacesStringFromJson(DataSnapshot dataSnapshot) throws JSONException {
        Log.d(LOG_TAG, "getPlacesStringFromJson()");
        ArrayList<HashMap<String, Object>> values = (ArrayList<HashMap<String, Object>>) dataSnapshot.getValue();
        //Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();
        Log.i(LOG_TAG, "Size:" + values.size());
        Log.d(LOG_TAG, values.toString());

        List<String> places = new ArrayList<>();
        for (HashMap<String, Object> placeMap : values) {
            if (placeMap != null) {
                String cityName = String.valueOf(placeMap.get(Constants.CITY));

                long cityId = addLocation(cityName);

                String placeType = String.valueOf(placeMap.get(Constants.PLACE_TYPE));

                long placeTypeId = addPlaceType(placeType);

                String placeName = String.valueOf(placeMap.get(Constants.NAME));
                String placeAddress = String.valueOf(placeMap.get(Constants.ADDRESS));
                String imageUrl = String.valueOf(placeMap.get(Constants.IMAGE_URL));
                String lat = String.valueOf(placeMap.get(Constants.LATITUDE));
                String lon = String.valueOf(placeMap.get(Constants.LONGITUDE));

                long placeId = addPlace(placeName, placeAddress, cityId, placeTypeId, imageUrl, lat, lon);

                Log.d(LOG_TAG, "The place " + placeName + " has been inserted with ID: " + placeId);


            }


        }

        for (String s : places) {
            Log.v(LOG_TAG, "Place entry: " + s);
        }

        return places;

    }


    private long addPlace(String placeName, String placeAddress, long locationId, long placeTypeId, String imageUrl, String lat, String lon) {
        Log.v(LOG_TAG, "addPlace()");

        Log.v(LOG_TAG, "Inserting " + placeName
                + " with address: " + placeAddress
                + ", location id:" + locationId
                + ", image url:" + imageUrl
                + ", type id:" + placeTypeId
                + ", lat:" + lat
                + ", lon:" + lon);

        Cursor cursor = getContext().getContentResolver().query(
                PlaceContract.PlaceEntry.CONTENT_URI,
                new String[]{PlaceContract.PlaceEntry._ID},
                PlaceContract.PlaceEntry.COLUMN_PLACE_NAME + " = ? ",
                new String[]{placeName},
                null);
        long returnValue;
        if (cursor.moveToFirst()) {
            Log.v(LOG_TAG, "'" + placeName + "' found in database");
            int placeIdIndex = cursor.getColumnIndex(PlaceContract.PlaceEntry._ID);

            returnValue = cursor.getLong(placeIdIndex);

        } else {
            Log.v(LOG_TAG, "Didn't find it in database, inserting now!");
            ContentValues placeValues = new ContentValues();
            placeValues.put(PlaceContract.PlaceEntry.COLUMN_PLACE_NAME, placeName);
            placeValues.put(PlaceContract.PlaceEntry.COLUMN_PLACE_ADDRESS, placeAddress);
            placeValues.put(PlaceContract.PlaceEntry.COLUMN_LOCATION_KEY, locationId);
            placeValues.put(PlaceContract.PlaceEntry.COLUMN_PLACE_IMAGE_URI, imageUrl);
            placeValues.put(PlaceContract.PlaceEntry.COLUMN_TYPE_KEY, placeTypeId);
            placeValues.put(PlaceContract.LocationEntry.COLUMN_COORD_LAT, lat);
            placeValues.put(PlaceContract.LocationEntry.COLUMN_COORD_LONG, lon);

            Uri placeInsertUri = getContext().getContentResolver()
                    .insert(PlaceContract.PlaceEntry.CONTENT_URI, placeValues);

            returnValue = ContentUris.parseId(placeInsertUri);
        }
        cursor.close();
        return returnValue;
    }

    private long addLocation(String cityName) {
        Log.v(LOG_TAG, "Inserting " + cityName);

        Cursor cursor = getContext().getContentResolver().query(
                PlaceContract.LocationEntry.CONTENT_URI,
                new String[]{PlaceContract.LocationEntry._ID},
                PlaceContract.LocationEntry.COLUMN_CITY_NAME + " = ? ",
                new String[]{cityName},
                null);


        long returnValue;
        if (cursor.moveToFirst()) {
            Log.v(LOG_TAG, "Found it in database");
            int locationIdIndex = cursor.getColumnIndex(PlaceContract.LocationEntry._ID);
            returnValue = cursor.getLong(locationIdIndex);
        } else {
            Log.v(LOG_TAG, "Didn't find it in database, inserting now!");
            ContentValues values = new ContentValues();
            values.put(PlaceContract.LocationEntry.COLUMN_CITY_NAME, cityName);

            Uri locationInsertUri = getContext().getContentResolver()
                    .insert(PlaceContract.LocationEntry.CONTENT_URI, values);

            returnValue = ContentUris.parseId(locationInsertUri);
        }
        cursor.close();
        return returnValue;
    }


    private long addPlaceType(String placeTypeName) {
        Log.v(LOG_TAG, "Inserting " + placeTypeName + ".");

        Cursor cursor = getContext().getContentResolver().query(
                PlaceContract.TypeEntry.CONTENT_URI,
                new String[]{PlaceContract.TypeEntry._ID},
                PlaceContract.TypeEntry.COLUMN_TYPE_NAME + " = ? ",
                new String[]{placeTypeName},
                null);

        long returnValue;
        if (cursor.moveToFirst()) {
            Log.v(LOG_TAG, "Found it in database");
            int placeTypeIdIndex = cursor.getColumnIndex(PlaceContract.TypeEntry._ID);
            returnValue =  cursor.getLong(placeTypeIdIndex);
        } else {
            Log.v(LOG_TAG, "Didn't find it in database, inserting now!");
            ContentValues placeTypeValues = new ContentValues();
            placeTypeValues.put(PlaceContract.TypeEntry.COLUMN_TYPE_NAME, placeTypeName);

            Uri placeTypeInsertUri = getContext().getContentResolver()
                    .insert(PlaceContract.TypeEntry.CONTENT_URI, placeTypeValues);

            returnValue = ContentUris.parseId(placeTypeInsertUri);
        }

        if(!cursor.isClosed()){
            cursor.close();
        }
        return returnValue;
    }

}
