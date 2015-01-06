package pe.apiconz.android.cooltura.app.test;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import java.util.Map;
import java.util.Set;

import pe.apiconz.android.cooltura.app.data.PlaceDbHelper;

import static pe.apiconz.android.cooltura.app.data.PlaceContract.LocationEntry;
import static pe.apiconz.android.cooltura.app.data.PlaceContract.PlaceEntry;
import static pe.apiconz.android.cooltura.app.data.PlaceContract.TypeEntry;

/**
 * Created by Indra on 04/01/2015.
 */
public class TestDb extends AndroidTestCase {
    private static final String LOG_TAG = TestDb.class.getSimpleName();
    public static final String TEST_CITY_NAME = "Lima";
    public static final double TEST_LATITUDE = -12.0553442;
    public static final double TEST_LONGITUDE = -77.0451853;
    public static final String TEST_TYPE_NAME = "museum";

    public void testCreateDb() throws Throwable {
        mContext.deleteDatabase(PlaceDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new PlaceDbHelper(this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());
        db.close();
    }

    public void testInsertDb() {
        PlaceDbHelper dbHelper = new PlaceDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Insert some value for Type and Location
        ContentValues typeValues = getTypeContentValues();
        long typeRowId;
        typeRowId = db.insert(TypeEntry.TABLE_NAME, null, typeValues);
        assertTrue(typeRowId != -1);
        Log.d(LOG_TAG, "New Type row id: " + typeRowId);

        Cursor typeCursor = db.query(TypeEntry.TABLE_NAME, null, null, null, null, null, null);

        if (typeCursor.moveToFirst()) {
            validateCursor(typeValues, typeCursor);


            ContentValues locationValues = getLocationContentValues();
            long locationRowId = db.insert(LocationEntry.TABLE_NAME, null, locationValues);
            assertTrue(locationRowId != -1);
            Log.d(LOG_TAG, "New Location row id: " + locationRowId);

            Cursor locationCursor = db.query(LocationEntry.TABLE_NAME, null, null, null, null, null, null);

            if (locationCursor.moveToFirst()) {
                validateCursor(locationValues, locationCursor);
            /*int locationNameIndex = locationCursor.getColumnIndex(LocationEntry.COLUMN_CITY_NAME);
            String cityName = locationCursor.getString(locationNameIndex);
            int locationLatitudeIndex = locationCursor.getColumnIndex(LocationEntry.COLUMN_COORD_LAT);
            double latitude = locationCursor.getDouble(locationLatitudeIndex);
            int locationLongitudeIndex = locationCursor.getColumnIndex(LocationEntry.COLUMN_COORD_LONG);
            double longitude = locationCursor.getDouble(locationLongitudeIndex);

            Log.d(LOG_TAG, "cityName=" + cityName + "; Latitude: " + latitude + "; Longitude:" + longitude);

            assertEquals(TEST_CITY_NAME, cityName);
            assertEquals(TEST_LATITUDE, latitude);
            assertEquals(TEST_LONGITUDE, longitude);*/


                ContentValues placeValues = getPlaceContentValues(typeRowId, locationRowId);

                long placeRowId = db.insert(PlaceEntry.TABLE_NAME, null, placeValues);
                assertTrue(placeRowId != -1);
                Log.d(LOG_TAG, "New Place row id: " + placeRowId);

                Cursor placeCursor = db.query(PlaceEntry.TABLE_NAME, null, null, null, null, null, null);

                if (placeCursor.moveToFirst()) {
                    validateCursor(placeValues, placeCursor);
                }

            } else {
                fail("No location returned");
            }

        } else {
            fail("No types returned");
        }

        dbHelper.close();

    }

    public static ContentValues getPlaceContentValues(long typeRowId, long locationRowId) {
        ContentValues placeValues = new ContentValues();
        placeValues.put(PlaceEntry.COLUMN_LOCATION_KEY, locationRowId);
        placeValues.put(PlaceEntry.COLUMN_TYPE_KEY, typeRowId);
        placeValues.put(PlaceEntry.COLUMN_PLACE_NAME, "Museo de Arte de Lima");
        placeValues.put(PlaceEntry.COLUMN_PLACE_ADDRESS, "Av. 28 de Julio esq Av. Garcilazo de la Vega");
        return placeValues;
    }

    static public ContentValues getTypeContentValues() {
        ContentValues values = new ContentValues();
        values.put(TypeEntry.COLUMN_TYPE_NAME, TEST_TYPE_NAME);
        return values;
    }

    static public ContentValues getLocationContentValues() {
        ContentValues locationValues = new ContentValues();

        locationValues.put(LocationEntry.COLUMN_CITY_NAME, TEST_CITY_NAME);
        locationValues.put(LocationEntry.COLUMN_COORD_LAT, TEST_LATITUDE);
        locationValues.put(LocationEntry.COLUMN_COORD_LONG, TEST_LONGITUDE);
        return locationValues;
    }

    public static void validateCursor(ContentValues expectedValues, Cursor valueCursor) {

        assertTrue(valueCursor.moveToFirst());

        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();

        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int index = valueCursor.getColumnIndex(columnName);
            assertFalse(-1 == index);
            if (entry.getValue() instanceof String) {
                String expectedValue = entry.getValue().toString();
                assertEquals(expectedValue, valueCursor.getString(index));
            } else if (entry.getValue() instanceof Double) {
                double expectedValue = ((Double) entry.getValue()).doubleValue();
                assertEquals(expectedValue, valueCursor.getDouble(index));
            } else {
                long expectedValue = ((Long) entry.getValue()).longValue();
                assertEquals(expectedValue, valueCursor.getLong(index));
            }
        }

        valueCursor.close();
    }
}
