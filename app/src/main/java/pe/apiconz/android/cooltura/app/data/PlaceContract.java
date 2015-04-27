package pe.apiconz.android.cooltura.app.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Indra on 04/01/2015.
 */
public class PlaceContract {

    public static final String CONTENT_AUTHORITY = "pe.apiconz.android.cooltura.app";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_PLACE = "place";
    public static final String PATH_LOCATION = "location";
    public static final String PATH_TYPE = "type";

    public static final class LocationEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_LOCATION).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_LOCATION;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_LOCATION;

        public static final String TABLE_NAME = "location";
        public static final String COLUMN_CITY_NAME = "city_name";
        public static final String COLUMN_COORD_LAT = "coord_lat";
        public static final String COLUMN_COORD_LONG = "coord_long";
        public static final String COLUMN_PLACE_IMAGE_URI = "image_uri";

        public static Uri buildLocationUri(long _id){
            return ContentUris.withAppendedId(CONTENT_URI,_id);
        }
    }

    public static final class TypeEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TYPE).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_TYPE;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_TYPE;

        public static final String TABLE_NAME = "type";
        public static final String COLUMN_TYPE_NAME = "type_name";

        public static Uri buildTypeUri(long _id){
            return ContentUris.withAppendedId(CONTENT_URI,_id);
        }
    }

    public static final class PlaceEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PLACE).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_PLACE;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_PLACE;

        public static final String TABLE_NAME = "place";
        public static final String COLUMN_PLACE_NAME = "place_name";
        public static final String COLUMN_PLACE_INFO = "place_info";
        public static final String COLUMN_PLACE_ADDRESS = "place_address";
        public static final String COLUMN_TYPE_KEY = "type_id";
        public static final String COLUMN_LOCATION_KEY = "location_id";
        public static final String COLUMN_PLACE_IMAGE_URI = "image_uri";

        public static Uri buildPlaceUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildPlacesByCityName(String cityName){
            return CONTENT_URI.buildUpon().appendPath(cityName).build();
        }

        public static Uri buildPlacesByCityNameWithType(String cityName,String typeName){
            return CONTENT_URI.buildUpon().appendPath(cityName).appendQueryParameter("type_name",typeName).build();
        }

        public static String getCityNameFromUri(Uri uri){
            return uri.getPathSegments().get(1);
        }

        public static String getTypeFromUri(Uri uri){
            return uri.getQueryParameter("type_name");
        }
    }
}
