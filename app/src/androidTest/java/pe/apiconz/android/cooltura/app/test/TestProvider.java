package pe.apiconz.android.cooltura.app.test;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

import pe.apiconz.android.cooltura.app.data.PlaceDbHelper;

import static pe.apiconz.android.cooltura.app.data.PlaceContract.LocationEntry;
import static pe.apiconz.android.cooltura.app.data.PlaceContract.PlaceEntry;
import static pe.apiconz.android.cooltura.app.data.PlaceContract.TypeEntry;

/**
 * Created by Indra on 04/01/2015.
 */
public class TestProvider extends AndroidTestCase {
    private static final String LOG_TAG = TestProvider.class.getSimpleName();

    public void testDeleteDb() throws Throwable {
        mContext.deleteDatabase(PlaceDbHelper.DATABASE_NAME);
    }

    public void testDeleteAllRecords() {
        mContext.getContentResolver().delete(PlaceEntry.CONTENT_URI, null, null);
        mContext.getContentResolver().delete(TypeEntry.CONTENT_URI, null, null);
        mContext.getContentResolver().delete(LocationEntry.CONTENT_URI, null, null);

        Cursor cursor = mContext.getContentResolver().query(PlaceEntry.CONTENT_URI, null, null, null, null);
        assertEquals(cursor.getCount(), 0);
        cursor.close();

        cursor = mContext.getContentResolver().query(LocationEntry.CONTENT_URI, null, null, null, null);
        assertEquals(cursor.getCount(), 0);
        cursor.close();

        cursor = mContext.getContentResolver().query(TypeEntry.CONTENT_URI, null, null, null, null);
        assertEquals(cursor.getCount(), 0);
        cursor.close();
    }

    public void testGetType() {
        // content://pe.apiconz.android.cooltura.app/place/
        String type = mContext.getContentResolver().getType(PlaceEntry.CONTENT_URI);
        // vnd.android.cursor.dir/pe.apiconz.android.cooltura.app/place
        assertEquals(PlaceEntry.CONTENT_TYPE, type);

        // content://pe.apiconz.android.cooltura.app/place/lima
        String testCityName = "Lima";
        type = mContext.getContentResolver().getType(PlaceEntry.buildPlacesByCityName(testCityName));
        // vnd.android.cursor.dir/pe.apiconz.android.cooltura.app/place
        assertEquals(PlaceEntry.CONTENT_TYPE, type);

        // content://pe.apiconz.android.cooltura.app/place/5
        long testPlaceId = 1;
        type = mContext.getContentResolver().getType(PlaceEntry.buildPlaceUri(testPlaceId));
        assertEquals(PlaceEntry.CONTENT_ITEM_TYPE, type);

        // content://pe.apiconz.android.cooltura.app/place/lima/museum
        String testTypeName = "museum";
        type = mContext.getContentResolver().getType(PlaceEntry.buildPlacesByCityNameWithType(testCityName, testTypeName));
        // vnd.android.cursor.dir/pe.apiconz.android.cooltura.app/place
        assertEquals(PlaceEntry.CONTENT_TYPE, type);

        // content://pe.apiconz.android.cooltura.app/place/1
        type = mContext.getContentResolver().getType(PlaceEntry.buildPlaceUri(1));
        // vnd.android.cursor.item/pe.apiconz.android.cooltura.app/place
        assertEquals(PlaceEntry.CONTENT_ITEM_TYPE, type);


        // content://pe.apiconz.android.cooltura.app/location/
        type = mContext.getContentResolver().getType(LocationEntry.CONTENT_URI);
        // vnd.android.cursor.dir/pe.apiconz.android.cooltura.app/location
        assertEquals(LocationEntry.CONTENT_TYPE, type);

        // content://pe.apiconz.android.cooltura.app/location/1
        type = mContext.getContentResolver().getType(LocationEntry.buildLocationUri(1));
        // vnd.android.cursor.item/pe.apiconz.android.cooltura.app/location
        assertEquals(LocationEntry.CONTENT_ITEM_TYPE, type);


        // content://pe.apiconz.android.cooltura.app/type/
        type = mContext.getContentResolver().getType(TypeEntry.CONTENT_URI);
        // vnd.android.cursor.dir/pe.apiconz.android.cooltura.app/type
        assertEquals(TypeEntry.CONTENT_TYPE, type);

        // content://pe.apiconz.android.cooltura.app/type/1
        type = mContext.getContentResolver().getType(TypeEntry.buildTypeUri(1));
        // vnd.android.cursor.item/pe.apiconz.android.cooltura.app/type
        assertEquals(TypeEntry.CONTENT_ITEM_TYPE, type);


    }

    public void testInsertReadDb() {
        PlaceDbHelper dbHelper = new PlaceDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Insert some value for Type and Location
        ContentValues typeValues = TestDb.getTypeContentValues();
        Uri insertUri  =  mContext.getContentResolver().insert(TypeEntry.CONTENT_URI, typeValues);
        long typeRowId = ContentUris.parseId(insertUri);
        assertTrue(typeRowId != -1);
        Log.d(LOG_TAG, "New Type row id: " + typeRowId);

        Cursor typeCursor = mContext.getContentResolver().query(TypeEntry.CONTENT_URI,
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null  // sort order
        );
        TestDb.validateCursor(typeValues, typeCursor);
        typeCursor.close();

        typeCursor = mContext.getContentResolver().query(
                TypeEntry.buildTypeUri(typeRowId),
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null  // sort order
        );
        TestDb.validateCursor(typeValues, typeCursor);
        typeCursor.close();


        ContentValues locationValues = TestDb.getLocationContentValues();
        insertUri = mContext.getContentResolver().insert(LocationEntry.CONTENT_URI, locationValues);
        long locationRowId = ContentUris.parseId(insertUri);
        assertTrue(locationRowId != -1);
        Log.d(LOG_TAG, "New Location row id: " + locationRowId);

        Cursor locationCursor = mContext.getContentResolver().query(LocationEntry.CONTENT_URI,
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        TestDb.validateCursor(locationValues, locationCursor);
        locationCursor.close();

        locationCursor = mContext.getContentResolver().query(
                LocationEntry.buildLocationUri(locationRowId),
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        TestDb.validateCursor(locationValues, locationCursor);
        locationCursor.close();

        ContentValues placeValues = TestDb.getPlaceContentValues(typeRowId, locationRowId);

        insertUri = mContext.getContentResolver().insert(PlaceEntry.CONTENT_URI, placeValues);
        long placeRowId = ContentUris.parseId(insertUri);
        assertTrue(placeRowId != -1);
        Log.d(LOG_TAG, "New Place row id: " + placeRowId);

        Cursor placeCursor = mContext.getContentResolver().query(PlaceEntry.CONTENT_URI,
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        TestDb.validateCursor(placeValues, placeCursor);
        placeCursor.close();

        placeCursor = mContext.getContentResolver().query(
                PlaceEntry.buildPlaceUri(1),
                null,
                null,
                null,
                null
        );
        if(placeCursor.moveToFirst()){
            Log.d(LOG_TAG, "Hay elementos");
        }

        TestDb.validateCursor(placeValues, placeCursor);
        placeCursor.close();

        placeCursor = mContext.getContentResolver().query(
                PlaceEntry.buildPlacesByCityName(TestDb.TEST_CITY_NAME),
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        TestDb.validateCursor(placeValues, placeCursor);
        placeCursor.close();


        placeCursor = mContext.getContentResolver().query(
                PlaceEntry.buildPlacesByCityNameWithType(TestDb.TEST_CITY_NAME, TestDb.TEST_TYPE_NAME),
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        TestDb.validateCursor(placeValues, placeCursor);
        placeCursor.close();


        dbHelper.close();

    }

}
