package pe.apiconz.android.cooltura.app.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Indra on 06/01/2015.
 */
public class PlaceSyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static PlaceSyncAdapter sPlaceSyncAdapter = null;
    @Override
    public void onCreate() {
        Log.d("SunshineSyncService", "onCreate - SunshineSyncService");
        synchronized (sSyncAdapterLock) {
            if (sPlaceSyncAdapter == null) {
                sPlaceSyncAdapter = new PlaceSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sPlaceSyncAdapter.getSyncAdapterBinder();
    }
}
