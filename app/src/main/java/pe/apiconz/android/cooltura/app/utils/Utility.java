package pe.apiconz.android.cooltura.app.utils;

import pe.apiconz.android.cooltura.app.R;

/**
 * Created by Armando on 03/01/2015.
 */
public class Utility {

    public static int getIconResourceForPlaceType(String placeTypeName) {
        switch (placeTypeName) {
            case "museum":
                return R.drawable.ic_museum;
            case "archaeological":
                return R.drawable.ic_archaeolo;
            case "theatre":
                return R.drawable.ic_theatre;
            case "monument":
                return R.drawable.ic_monument;
            case "church":
                return R.drawable.ic_church;
            default:
                return R.drawable.ic_place;
        }
    }
}
