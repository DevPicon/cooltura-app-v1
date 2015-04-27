package pe.apiconz.android.cooltura.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import pe.apiconz.android.cooltura.app.R;
import pe.apiconz.android.cooltura.app.sync.PlaceSyncAdapter;


public class MainActivity extends AppCompatActivity implements PlaceListFragment.Callback {

    private boolean mTwoPane = false;
    private String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.place_detail_container) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.place_detail_container, new PlaceDetailFragment())
                        .commit();
            }
        } else {
            mTwoPane = false;
        }

        // Show home icon
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);

        PlaceSyncAdapter.initializeSyncAdapter(this);
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

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_refresh){
            FetchPlacesTask placesTask = new FetchPlacesTask();
            //TODO: Temporarily I send country code to get places list
            placesTask.execute("PE");
            return true;
        }*/


        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }else if(id == R.id.action_about){
            DialogFragment newFragment = new AboutFragment();
            newFragment.show(getSupportFragmentManager(), "");
        }else if(id == R.id.action_refresh){

        }

        return super.onOptionsItemSelected(item);
    }


}
