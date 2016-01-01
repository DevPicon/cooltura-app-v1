package pe.apiconz.android.cooltura.app.presentation.activities;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import pe.apiconz.android.cooltura.app.R;
import pe.apiconz.android.cooltura.app.presentation.fragments.AboutFragment;
import pe.apiconz.android.cooltura.app.presentation.fragments.ShinPlaceListFragment;


public class MainActivity extends BaseActivity {

    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setSupportActionBar() {
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    protected void onCreate() {
        Log.d(TAG, "Paso por onCreate");
        setSupportActionBar();


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ShinPlaceListFragment fragment = new ShinPlaceListFragment();
        fragmentTransaction.replace(R.id.fragment_place, fragment);
        fragmentTransaction.commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_settings:

                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.action_about:

                DialogFragment newFragment = new AboutFragment();
                newFragment.show(getSupportFragmentManager(), "");
                return true;

            default:

                return super.onOptionsItemSelected(item);
        }
    }


}
