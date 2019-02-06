package com.example.braintech.projecttest;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

public class HomepageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    SharedPreferences sharedpreferences;
    Fragment homefragment;

    TabLayout tabLayout;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        getId();

        enterDetails();
        addFragment(new HomeFragment());

        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.nav_open,R.string.nav_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition())
                {
                    case 0:
                        homefragment = new HomeFragment();
                        loadFragment(homefragment);
                        break;
                    case 1:
                        homefragment = new ProfileFragment();
                        loadFragment(homefragment);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }


    public void enterDetails()
    {
        TabLayout.Tab Home = tabLayout.newTab();
        Home.setIcon(R.drawable.drawer_home);
        Home.setText("Home");
        tabLayout.addTab(Home);

        TabLayout.Tab Profile = tabLayout.newTab();
        Profile.setIcon(R.drawable.bottom_profile_clipart);
        Profile.setText("Profile");
        tabLayout.addTab(Profile);

    }
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

            Toast.makeText(getApplication(),"Welcome Home",Toast.LENGTH_SHORT).show();
        }
        else if (menuItem.getItemId() == R.id.Logout)
        {
            Log.d("Drawer-------->","Logout.....");
            Toast.makeText(getApplication(),"Logout Successfull",Toast.LENGTH_SHORT).show();
            clearPref();
            finish();
        }
        return false;
    }
    private void clearPref()
    {
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(Const.EMAIL, null);
        editor.putString(Const.PASSWORD, null);
        editor.commit();
    }

    private void addFragment(Fragment fragment)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.drawerlayout, fragment);
       // transaction.addToBackStack(null);
        transaction.commit();
    }
    private void loadFragment(Fragment fragment) {
        // load fragment

        Log.d("LoadFragment-------->","Loading.....");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.drawerlayout, fragment);
       /* transaction.addToBackStack(null);*/
        transaction.commit();
    }


    public void getId()
    {
        drawerLayout = (DrawerLayout)findViewById(R. id.drawerlayout);
        navigationView = (NavigationView)findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        sharedpreferences = getSharedPreferences(Const.MyPREFERENCES, Context.MODE_PRIVATE);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
    }
}
