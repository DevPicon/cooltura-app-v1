package pe.apiconz.android.cooltura.app.model.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

/**
 * Created by Indra on 05/01/2015.
 */
public class PlaceProvider extends ContentProvider {

    private static final String LOG_TAG = PlaceProvider.class.getSimpleName();

    private static final int PLACE = 100;
    private static final int PLACE_ID = 101;
    private static final int PLACE_BY_LOCATION = 102;
    private static final int PLACE_BY_CITY_NAME_WITH_TYPE = 103;
    private static final int LOCATION = 200;
    private static final int LOCATION_ID = 201;
    private static final int TYPE = 300;
    private static final int TYPE_ID = 301;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private PlaceDbHelper mOpenHelper;
    private static final SQLiteQueryBuilder sPlaceLocationTypeQueryBuilder;

    static {
        sPlaceLocationTypeQueryBuilder = new SQLiteQueryBuilder();
        sPlaceLocationTypeQueryBuilder.setTables(PlaceContract.PlaceEntry.TABLE_NAME + " INNER JOIN " +
                        PlaceContract.LocationEntry.TABLE_NAME +
                        " ON " + PlaceContract.PlaceEntry.TABLE_NAME +
                        "." + PlaceContract.PlaceEntry.COLUMN_LOCATION_KEY +
                        " = " + PlaceContract.LocationEntry.TABLE_NAME +
                        "." + PlaceContract.LocationEntry._ID +
                        " INNER JOIN " +
                        PlaceContract.TypeEntry.TABLE_NAME +
                        " ON " + PlaceContract.PlaceEntry.TABLE_NAME +
                        "." + PlaceContract.PlaceEntry.COLUMN_TYPE_KEY +
                        " = " + PlaceContract.TypeEntry.TABLE_NAME +
                        "." + PlaceContract.TypeEntry._ID
        );
    }
    private static final String sPlaceId = PlaceContract.PlaceEntry.TABLE_NAME + "." +
            PlaceContract.PlaceEntry._ID + " = ? ";
    private static final String sLocationSelection = PlaceContract.LocationEntry.TABLE_NAME +
            "." + PlaceContract.LocationEntry.COLUMN_CITY_NAME + " = ? ";
    private static final String sLocationWithTypeSelection = PlaceContract.LocationEntry.TABLE_NAME +
            "." + PlaceContract.LocationEntry.COLUMN_CITY_NAME + " = ? AND " +
            PlaceContract.TypeEntry.TABLE_NAME +
            "." + PlaceContract.TypeEntry.COLUMN_TYPE_NAME + " = ?";

    private Cursor getPlaceById(Uri uri, String[] projection, String sortOrder) {
        long placeId = ContentUris.parseId(uri);
        Log.d(LOG_TAG, "placeId=" + placeId);

        String[] selectionArgs;
        String selection;

        selection = sPlaceId;
        selectionArgs = new String[]{"" + placeId};

        return sPlaceLocationTypeQueryBuilder.query(
                mOpenHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
    }
    private Cursor getPlacesByCityName(Uri uri, String[] projection, String sortOrder) {
        String cityName = PlaceContract.PlaceEntry.getCityNameFromUri(uri);
        String type = PlaceContract.PlaceEntry.getTypeFromUri(uri);

        Log.d(LOG_TAG, "cityName=" + cityName + "; type=" + type);

        String[] selectionArgs;
        String selection;

        if (type == null) {
            selection = sLocationSelection;
            selectionArgs = new String[]{cityName};
        } else {
            selectionArgs = new String[]{cityName, type};
            selection = sLocationWithTypeSelection;
        }

        return sPlaceLocationTypeQueryBuilder.query(
                mOpenHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = PlaceContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, PlaceContract.PATH_PLACE, PLACE);
        matcher.addURI(authority, PlaceContract.PATH_PLACE + "/#", PLACE_ID);
        matcher.addURI(authority, PlaceContract.PATH_PLACE + "/*", PLACE_BY_LOCATION);
        matcher.addURI(authority, PlaceContract.PATH_PLACE + "/*/*", PLACE_BY_CITY_NAME_WITH_TYPE);

        matcher.addURI(authority, PlaceContract.PATH_LOCATION, LOCATION);
        matcher.addURI(authority, PlaceContract.PATH_LOCATION + "/#", LOCATION_ID);

        matcher.addURI(authority, PlaceContract.PATH_TYPE, TYPE);
        matcher.addURI(authority, PlaceContract.PATH_TYPE + "/#", TYPE_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new PlaceDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case PLACE_BY_CITY_NAME_WITH_TYPE: {
                retCursor = getPlacesByCityName(uri, projection, sortOrder);
                break;
            }
            case PLACE_BY_LOCATION: {
                retCursor = getPlacesByCityName(uri, projection, sortOrder);
                break;
            }
            case PLACE_ID: {
                retCursor = getPlaceById(uri, projection, sortOrder);
                break;
            }
            case PLACE: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        PlaceContract.PlaceEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case LOCATION_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        PlaceContract.LocationEntry.TABLE_NAME,
                        projection,
                        PlaceContract.LocationEntry._ID + " = '" + ContentUris.parseId(uri) + "'",
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case LOCATION: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        PlaceContract.LocationEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case TYPE_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        PlaceContract.TypeEntry.TABLE_NAME,
                        projection,
                        PlaceContract.TypeEntry._ID + " = '" + ContentUris.parseId(uri) + "'",
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case TYPE: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        PlaceContract.TypeEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }


    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PLACE_BY_CITY_NAME_WITH_TYPE:
                return PlaceContract.PlaceEntry.CONTENT_TYPE;
            case PLACE_BY_LOCATION:
                return PlaceContract.PlaceEntry.CONTENT_TYPE;
            case PLACE_ID:
                return PlaceContract.PlaceEntry.CONTENT_ITEM_TYPE;
            case PLACE:
                return PlaceContract.PlaceEntry.CONTENT_TYPE;
            case LOCATION_ID:
                return PlaceContract.LocationEntry.CONTENT_ITEM_TYPE;
            case LOCATION:
                return PlaceContract.LocationEntry.CONTENT_TYPE;
            case TYPE_ID:
                return PlaceContract.TypeEntry.CONTENT_ITEM_TYPE;
            case TYPE:
                return PlaceContract.TypeEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri returnUri;
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PLACE: {
                long _id = mOpenHelper.getWritableDatabase().insert(PlaceContract.PlaceEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = PlaceContract.PlaceEntry.buildPlaceUri(_id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case LOCATION: {
                long _id = mOpenHelper.getWritableDatabase().insert(PlaceContract.LocationEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = PlaceContract.LocationEntry.buildLocationUri(_id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case TYPE: {
                long _id = mOpenHelper.getWritableDatabase().insert(PlaceContract.TypeEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = PlaceContract.TypeEntry.buildTypeUri(_id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int rowsDeleted;
        final int match = sUriMatcher.match(uri);
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        switch (match) {
            case PLACE: {
                rowsDeleted = db.delete(PlaceContract.PlaceEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            case LOCATION: {
                rowsDeleted = db.delete(PlaceContract.LocationEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            case TYPE: {
                rowsDeleted = db.delete(PlaceContract.TypeEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }

        if (null == selection || 0 != rowsDeleted) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int rowsUpdated;
        final int match = sUriMatcher.match(uri);
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        switch (match) {
            case PLACE: {
                rowsUpdated = db.update(PlaceContract.PlaceEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            }
            case LOCATION: {
                rowsUpdated = db.update(PlaceContract.LocationEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            }
            case TYPE: {
                rowsUpdated = db.update(PlaceContract.TypeEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }

        if (0 != rowsUpdated) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PLACE:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(PlaceContract.PlaceEntry.TABLE_NAME, null, value);
                        if (-1 != _id) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }
}
