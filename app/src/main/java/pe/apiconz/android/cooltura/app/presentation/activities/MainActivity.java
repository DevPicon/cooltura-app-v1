package pe.apiconz.android.cooltura.app.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import pe.apiconz.android.cooltura.app.R;
import pe.apiconz.android.cooltura.app.presentation.fragments.AboutFragment;
import pe.apiconz.android.cooltura.app.presentation.fragments.PlaceDetailFragment;
import pe.apiconz.android.cooltura.app.presentation.fragments.PlaceListFragment;


public class MainActivity extends BaseActivity implements PlaceListFragment.Callback {

    private boolean mTwoPane = false;
    private String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (findViewById(R.id.place_detail_container) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {

                PlaceDetailFragment fragment = new PlaceDetailFragment();

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.place_detail_container, fragment)
                        .commit();
            }
        } else {
            mTwoPane = false;
        }

        // Show home icon
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);

    }*/

    @Override
    protected void onCreate() {

        if (findViewById(R.id.place_detail_container) != null) {
            mTwoPane = true;

            PlaceDetailFragment fragment = new PlaceDetailFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.place_detail_container, fragment)
                    .commit();

        }
        else {
            mTwoPane = false;
        }

        // Show home icon
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }


    @Override
    public void onItemSelected(long placeId) {
        if (mTwoPane) {
            Bundle args = new Bundle();
            args.putLong(PlaceDetailFragment.PLACE_KEY, placeId);

            PlaceDetailFragment fragment = new PlaceDetailFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction().replace(R.id.place_detail_container, fragment).commit();
        } else {
            Intent intent = new Intent(this, PlaceDetailActivity.class)
                    .putExtra(PlaceDetailFragment.PLACE_KEY, placeId);

            startActivity(intent);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Log.v(LOG_TAG, "Invoke menu  option");

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
