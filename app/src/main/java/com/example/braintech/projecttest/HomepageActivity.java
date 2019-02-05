package com.example.braintech.projecttest;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

public class HomepageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        drawerLayout = (DrawerLayout)findViewById(R. id.drawerlayout);
        navigationView = (NavigationView)findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(selectItem);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.nav_open,R.string.nav_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener selectItem
            = new BottomNavigationView.OnNavigationItemSelectedListener(){

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment homefragment;
            switch (menuItem.getItemId())
            {
                case R.id.bottom_homeMenu:
                    Log.d("LoadFragment-------->","Switch.....");
                    homefragment = new HomeFragment();
                    loadFragment(homefragment);
            }
           return false ;
        }
    };
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (toggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.homeMenu)
        {
            Log.d("Drawer-------->","Home.....");
            Toast.makeText(getApplication(),"Welcome Home",Toast.LENGTH_SHORT).show();
        }
        else if (menuItem.getItemId() == R.id.Logout)
        {
            Log.d("Drawer-------->","Logout.....");
            Toast.makeText(getApplication(),"Logout Successfull",Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void loadFragment(Fragment fragment) {
        // load fragment

        Log.d("LoadFragment-------->","Loading.....");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.drawerlayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
