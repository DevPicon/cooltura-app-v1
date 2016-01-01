package pe.apiconz.android.cooltura.app.presentation.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import pe.apiconz.android.cooltura.app.R;
import pe.apiconz.android.cooltura.app.presentation.fragments.PlaceDetailFragment;


public class PlaceDetailActivity extends BaseActivity {

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_place_detail;
    }

    @Override
    protected void setSupportActionBar() {

        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white);
            supportActionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {

            long placeId = getIntent().getLongExtra(PlaceDetailFragment.PLACE_KEY, -1);

            Bundle arguments = new Bundle();
            arguments.putLong(PlaceDetailFragment.PLACE_KEY, placeId);

            PlaceDetailFragment fragment = new PlaceDetailFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.place_detail_container, fragment)
                    .commit();
        }
    }*/

    @Override
    protected void onCreate() {

        long placeId = getIntent().getLongExtra(PlaceDetailFragment.PLACE_KEY, -1);

        Bundle arguments = new Bundle();
        arguments.putLong(PlaceDetailFragment.PLACE_KEY, placeId);

        PlaceDetailFragment fragment = new PlaceDetailFragment();
        fragment.setArguments(arguments);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.place_detail_container, fragment)
                .commit();
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
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }


}
