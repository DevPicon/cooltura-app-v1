package pe.apiconz.android.cooltura.app.model.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static pe.apiconz.android.cooltura.app.model.data.PlaceContract.*;

/**
 * Created by Indra on 04/01/2015.
 */
public class PlaceDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "places.db";

    public PlaceDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE " + LocationEntry.TABLE_NAME + " (" +
                LocationEntry._ID + " INTEGER PRIMARY KEY," +
                LocationEntry.COLUMN_CITY_NAME + " TEXT NOT NULL" +
                ")";

        final String SQL_CREATE_TYPE_TABLE = "CREATE TABLE " + TypeEntry.TABLE_NAME + " (" +
                TypeEntry._ID + " INTEGER PRIMARY KEY," +
                TypeEntry.COLUMN_TYPE_NAME + " TEXT NOT NULL " +
                ")";

        final String SQL_CREATE_PLACE_TABLE = "CREATE TABLE " + PlaceEntry.TABLE_NAME + " (" +
                PlaceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PlaceEntry.COLUMN_LOCATION_KEY + " INTEGER NOT NULL," +
                PlaceEntry.COLUMN_TYPE_KEY + " INTEGER NOT NULL," +
                PlaceEntry.COLUMN_PLACE_NAME + " TEXT NOT NULL," +
                PlaceEntry.COLUMN_PLACE_INFO + " TEXT," +
                PlaceEntry.COLUMN_PLACE_IMAGE_URI + " TEXT,"  +
                LocationEntry.COLUMN_COORD_LAT + " TEXT NOT NULL," +
                LocationEntry.COLUMN_COORD_LONG + " TEXT NOT NULL," +
                PlaceEntry.COLUMN_PLACE_ADDRESS + " TEXT NOT NULL, " +
                " FOREIGN KEY (" + PlaceEntry.COLUMN_LOCATION_KEY + ") REFERENCES " +
                LocationEntry.TABLE_NAME + "(" + LocationEntry._ID + ")," +
                " FOREIGN KEY (" + PlaceEntry.COLUMN_TYPE_KEY + ") REFERENCES " +
                TypeEntry.TABLE_NAME + "(" + TypeEntry._ID + ")" +
                 ")";

        db.execSQL(SQL_CREATE_LOCATION_TABLE);
        db.execSQL(SQL_CREATE_TYPE_TABLE);
        db.execSQL(SQL_CREATE_PLACE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + PlaceEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + LocationEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TypeEntry.TABLE_NAME);
        onCreate(db);

    }
}
