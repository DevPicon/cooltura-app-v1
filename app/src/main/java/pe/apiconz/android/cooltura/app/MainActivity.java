package pe.apiconz.android.cooltura.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class MainActivity extends ActionBarActivity implements PlaceListFragment.Callback {

    private boolean mTwoPane = false;

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

/*
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
        Log.v(LOG_TAG, "Invoke refresh option");

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh){
            FetchPlacesTask placesTask = new FetchPlacesTask();
            //TODO: Temporarily I send country code to get places list
            placesTask.execute("PE");
            return true;
        }


        if (id == R.id.action_refresh) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/


}
