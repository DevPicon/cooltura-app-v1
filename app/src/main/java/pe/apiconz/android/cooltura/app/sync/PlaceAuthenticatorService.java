package pe.apiconz.android.cooltura.app.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Indra on 06/01/2015.
 */
public class PlaceAuthenticatorService extends Service {
    private PlaceAuthenticator mAuthenticator;

    @Override
    public void onCreate() {

        mAuthenticator = new PlaceAuthenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
