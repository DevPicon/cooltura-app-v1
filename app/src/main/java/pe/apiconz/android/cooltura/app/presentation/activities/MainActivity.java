package pe.apiconz.android.cooltura.app.presentation.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.Bind;
import pe.apiconz.android.cooltura.app.R;
import pe.apiconz.android.cooltura.app.presentation.fragments.AboutFragment;
import pe.apiconz.android.cooltura.app.presentation.fragments.ShinPlaceListFragment;
import pe.apiconz.android.cooltura.app.presentation.fragments.WifiPlaceListFragment;


public class MainActivity extends BaseActivity {

    @Bind(R.id.drawer_layout)
    protected DrawerLayout drawerLayout;
    @Bind(R.id.nav_view)
    protected NavigationView navigationView;
    @Bind(R.id.fab)
    protected FloatingActionButton fab;
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


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);

                displayView(item.getItemId());

                return true;
            }
        });

        displayView(R.id.nav_places);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Añadir nuevo lugar!", Snackbar.LENGTH_LONG).show();
                //TODO: Mostrar el formualario para agregar un nuevo lugar
            }
        });

    }

    private void displayView(int viewId) {
        Fragment fragment = null;

        switch (viewId) {
            case R.id.nav_places:
                fragment = new ShinPlaceListFragment();
                break;
            case R.id.nav_wifi:
                fragment = new WifiPlaceListFragment();
                break;

        }

        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_place, fragment);
            fragmentTransaction.commit();
        }

        drawerLayout.closeDrawers();
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
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

            default:

                return super.onOptionsItemSelected(item);
        }
    }


}
