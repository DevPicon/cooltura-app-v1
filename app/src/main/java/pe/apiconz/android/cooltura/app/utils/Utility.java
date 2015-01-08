package pe.apiconz.android.cooltura.app.utils;

import pe.apiconz.android.cooltura.app.R;

/**
 * Created by Indra on 03/01/2015.
 */
public class Utility {

    public static int getIconResourceForPlaceType(String placeTypeName) {
        if(placeTypeName.equalsIgnoreCase("museum")){
            return R.drawable.ic_museum;
        } else if(placeTypeName.equalsIgnoreCase("archaeological")){
            return R.drawable.ic_archaeolo;
        } else if(placeTypeName.equalsIgnoreCase("theatre")){
            return R.drawable.ic_theatre;
        } else if(placeTypeName.equalsIgnoreCase("monument")){
            return R.drawable.ic_monument;
        } else if(placeTypeName.equalsIgnoreCase("church")){
            return R.drawable.ic_church;
        } else{
            return R.drawable.ic_place;
        }
    }
}
