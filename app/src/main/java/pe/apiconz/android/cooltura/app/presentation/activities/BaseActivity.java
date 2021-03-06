package pe.apiconz.android.cooltura.app.presentation.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;
import pe.apiconz.android.cooltura.app.R;

/**
 * Created by Armando on 11/14/2015.
 */
public abstract class BaseActivity extends AppCompatActivity {


    @Bind(R.id.toolbar)
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        ButterKnife.bind(this);
        onCreate();
    }


    protected abstract void onCreate();
    protected abstract int getLayoutResourceId();

    protected abstract void setSupportActionBar();

}
