package pe.apiconz.android.cooltura.app.presentation.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Armando on 11/14/2015.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());

        onCreate();
    }

    protected abstract void onCreate();
    protected abstract int getLayoutResourceId();

}
