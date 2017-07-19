package com.zenithstudios.michael.boxofficepredictor;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, PredictorFragment.TotalSender {

    NavigationView navigationView = null;
    Toolbar toolbar = null;

    String finalResult;
    boolean drawerItemClicked;
    int drawerItemId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set the fragment initially
        MainFragment fragment = new MainFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View view, float v) {

            }

            @Override
            public void onDrawerOpened(View view) {

            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
            public void onDrawerClosed(View view) {
                if (drawerItemClicked)
                    selectNavItem();
                drawerItemClicked = false;
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(

                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit the app?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.super.onBackPressed();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }


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


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        // Handle navigation view item clicks here.
        int id = item.getItemId();


        drawerItemClicked = true;
        drawerItemId = id;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void selectNavItem(){
        if (drawerItemId == R.id.nav_home) {
            MainFragment fragment = new MainFragment();
            setupFragment(fragment);

        } else if (drawerItemId == R.id.nav_predictor) {
            PredictorFragment fragment = new PredictorFragment();
            setupFragment(fragment);


        } else if (drawerItemId == R.id.nav_about) {
            AboutFragment fragment =  new AboutFragment();
            setupFragment(fragment);

        } else if (drawerItemId == R.id.nav_contact) {
            ContactFragment fragment = new ContactFragment();
            setupFragment(fragment);
        }
    }

    protected void setupFragment(Fragment fragment) {
        try {
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        }catch(Exception ex){

        }
    }


    // I put the onclick method here because the xml needs to use a method from the main activity, not a fragment
    public void onclick(View v){
        navigationView.setCheckedItem(R.id.nav_predictor);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                PredictorFragment fragment = new PredictorFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
            }
        }, 300);

    }

    public void displayContact(View v){
        navigationView.setCheckedItem(R.id.nav_contact);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ContactFragment fragment = new ContactFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
            }
        }, 300);
    }

    public void displayMojo(View v){
        WebFragment fragment = new WebFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void sendTotal(String total) {
        // this piece of code doesn't actually do anything; It's just a reminder of another way to do this
        finalResult = total;

        //Creates a new results fragment and passes the total to it
        Bundle bundle = new Bundle();
        bundle.putString("Prediction", total);
        ResultsFragment fragment = new ResultsFragment();
        fragment.setArguments(bundle);
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();



    }

    /* This is the function for getting the multiplying factor :)
    private static double getMulFactor(String genresaved, int monthsaved) {
        if (genresaved.equalsIgnoreCase("Action")) {
            switch (monthsaved) {
                case 1:
                    return 3.247;
                case 2:
                    return 3.257;
                case 3:
                    return 2.746;
                case 4:
                    return 2.474;
                case 5:
                    return 2.455;
                case 6:
                    return 2.663;
                case 7:
                    return 3.210;
                case 8:
                    return 3.230;
                case 9:
                    return 3.571;
                case 10:
                    return 3.656;
                case 11:
                    return 2.787;
                case 12:
                    return 4.415;
            }
        } else if (genresaved.equalsIgnoreCase("Drama")) {
            switch (monthsaved) {
                case 1:
                    return 3.672;
                case 2:
                    return 3.02;
                case 3:
                    return 3.399;
                case 4:
                    return 3.373;
                case 5:
                    return 3.197;
                case 6:
                    return 3.470;
                case 7:
                    return 3.432;
                case 8:
                    return 3.834;
                case 9:
                    return 2.966;
                case 10:
                    return 4.144;
                case 11:
                    return 3.339;
                case 12:
                    return 4.890;
            }
        } else if (genresaved.equalsIgnoreCase("Horror")) {
            switch (monthsaved) {
                case 1:
                    return 2.022;
                case 2:
                    return 2.047;
                case 3:
                    return 2.217;
                case 4:
                    return 2.015;
                case 5:
                    return 2.105;
                case 6:
                    return 2.037;
                case 7:
                    return 2.551;
                case 8:
                    return 2.195;
                case 9:
                    return 2.229;
                case 10:
                    return 2.160;
                case 11:
                    return 2.186;
                case 12:
                    return 3.273;
            }
        } else if (genresaved.equalsIgnoreCase("Comedy")) {
            switch (monthsaved) {
                case 1:
                    return 3.123;
                case 2:
                    return 3.378;
                case 3:
                    return 3.824;
                case 4:
                    return 3.117;
                case 5:
                    return 3.264;
                case 6:
                    return 3.737;
                case 7:
                    return 3.238;
                case 8:
                    return 3.214;
                case 9:
                    return 3.63;
                case 10:
                    return 3.157;
                case 11:
                    return 3.286;
                case 12:
                    return 4.66;
            }
        } else if (genresaved.equalsIgnoreCase("Animated")) {
            switch (monthsaved) {
                case 1:
                    return 3.247;
                case 2:
                    return 3.68;
                case 3:
                    return 3.505;
                case 4:
                    return 3.534;
                case 5:
                    return 4.06;
                case 6:
                    return 3.501;
                case 7:
                    return 4.16;
                case 8:
                    return 4.184;
                case 9:
                    return 3.527;
                case 10:
                    return 3.737;
                case 11:
                    return 4.032;
                case 12:
                    return 5.133;
            }
        }
        return -1;
    }
    */
}


