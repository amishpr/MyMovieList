package edu.psu.avp5564.mymovielist.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import edu.psu.avp5564.mymovielist.fragments.MovieFragment;
import edu.psu.avp5564.mymovielist.R;
import edu.psu.avp5564.mymovielist.dummy.DummyContent;
import edu.psu.avp5564.mymovielist.fragments.SearchFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MovieFragment.OnListFragmentInteractionListener,
        SearchFragment.OnFragmentInteractionListener
    {

        DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        DrawerLayout mainLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        readTheme(drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

        @Override
        protected void onResume() {
            readTheme(drawer);
            super.onResume();
        }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent myIntent = new Intent(this, SettingsActivity.class);
            startActivity(myIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        String title = getString(R.string.app_name);

        if (id == R.id.nav_list) {

            Intent myIntent = new Intent(this, MovieListActivity.class);
            startActivity(myIntent);
//            fragment = new MovieFragment();
        } else if (id == R.id.nav_search) {
            Intent myIntent = new Intent(this, SearchActivity.class);
            startActivity(myIntent);
//            fragment = new SearchFragment();
        }
        else if (id == R.id.nav_settings) {
            Intent myIntent = new Intent(this, SettingsActivity.class);
            startActivity(myIntent);
        }
        else if (id == R.id.nav_logout) {
            Intent myIntent = new Intent(this, LoginActivity.class);
            startActivity(myIntent);
        }
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.contentMain, fragment);
            ft.commit();
        }

//        // set the toolbar title
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setTitle(title);
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

        @Override
        public void onFragmentInteraction(Uri uri) {

        }

        public void readTheme(DrawerLayout drawerLayout) {
            SharedPreferences appSharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(this.getApplicationContext());

            String theme = appSharedPrefs.getString(getString(R.string.THEME_KEY), null);

            setTheme(theme, drawerLayout);
        }

        public void setTheme(String theme, DrawerLayout drawerLayout) {

            if (theme.equals(getString(R.string.WHITE_CAP))) {
//            setTheme(R.style.AppTheme);
                drawerLayout.setBackgroundColor(Color.WHITE);
            }
            else if (theme.equals(getString(R.string.GRAY_CAP))) {
                drawerLayout.setBackgroundColor(Color.GRAY);
            }
            else if (theme.equals(getString(R.string.GREEN_CAP))) {
                drawerLayout.setBackgroundColor(Color.GREEN);
            }
            else if (theme.equals(getString(R.string.BLUE_CAP))) {
                drawerLayout.setBackgroundColor(Color.BLUE);
            }
            else if (theme.equals(getString(R.string.RED_CAP))) {
                drawerLayout.setBackgroundColor(Color.RED);
            }
        }
    }
