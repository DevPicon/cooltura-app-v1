package pe.apiconz.android.cooltura.app.application;

import android.app.Application;

import com.firebase.client.Firebase;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by Armando on 11/16/2015.
 */
public class CoolturaApplication extends Application {

    private String FIREBASE_URL = "https://cooltura-app.firebaseio.com/";
    private String FIREBASE_CHILD_CITIES = "cities";
    private String FIREBASE_CHILD_PLACES = "places";
    private String FIREBASE_CHILD_TYPES = "types";
    private String FIREBASE_CHILD_USERS = "users";

    private Firebase firebase;

    @Override
    public void onCreate() {
        super.onCreate();
        setupFirebase();
        setupUniversalImageLoader();
    }

    private void setupUniversalImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();

        ImageLoader.getInstance().init(config);

    }

    private void setupFirebase() {
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        firebase = new Firebase(FIREBASE_URL);
    }

    public Firebase getFirebaseReferenceForPlaces() {
        return firebase.child(FIREBASE_CHILD_PLACES);
    }

    public Firebase getFirebaseReferenceForUsers() {
        return firebase.child(FIREBASE_CHILD_USERS);
    }

    public Firebase getFirebaseReferenceForCities() {
        return firebase.child(FIREBASE_CHILD_CITIES);
    }

    public Firebase getFirebaseReferenceForTypes() {
        return firebase.child(FIREBASE_CHILD_TYPES);
    }
}
