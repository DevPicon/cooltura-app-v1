package pe.apiconz.android.cooltura.app.application;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Armando on 11/16/2015.
 */
public class CoolturaApplication extends Application {

    private String FIREBASE_URL= "https://cooltura-app.firebaseio.com/";
    private String FIREBASE_CHILD_CITIES = "cities";
    private String FIREBASE_CHILD_PLACES = "places";
    private String FIREBASE_CHILD_TYPES = "types";
    private String FIREBASE_CHILD_USERS = "users";

    Firebase firebase;

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        firebase = new Firebase(FIREBASE_URL);
    }

    public Firebase getFirebaseReferenceForPlaces(){
        return firebase.child(FIREBASE_CHILD_PLACES);
    }

    public Firebase getFirebaseReferenceForUsers(){
        return firebase.child(FIREBASE_CHILD_USERS);
    }

    public Firebase getFirebaseReferenceForCities(){
        return firebase.child(FIREBASE_CHILD_CITIES);
    }

    public Firebase getFirebaseReferenceForTypes(){
        return firebase.child(FIREBASE_CHILD_TYPES);
    }
}
