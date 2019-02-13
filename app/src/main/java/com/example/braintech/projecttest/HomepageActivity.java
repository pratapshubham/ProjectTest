package com.example.braintech.projecttest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.braintech.projecttest.Model.UserModel;
import com.google.android.gms.common.api.GoogleApiClient;

public class HomepageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    SharedPreferences sharedpreferences;
    Fragment homefragment;
    TextView txt_displayname;
    TabLayout tabLayout;
    GoogleApiClient googleApiClient;
    AlertDialog.Builder builder;
    SharedPreferences sharedPreferences;
    DatabaseHandlerClass databaseHandlerClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        setTitle("Dashboard");
        getId();

        enterDetails();
        addFragment(new HomeFragment());

        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.nav_open,R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        displayName();

        selectTablayout();

    }

    public void displayName()
    {
        databaseHandlerClass = new DatabaseHandlerClass(getApplicationContext());
        sharedpreferences = getApplicationContext().getSharedPreferences(Const.MyPREFERENCES, Context.MODE_PRIVATE);
        String email = sharedpreferences.getString(Const.EMAIL,null);
        UserModel userModel = databaseHandlerClass.displayusername(email);
        txt_displayname.setText(userModel.getName());
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

    public void selectTablayout()
    {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition())
                {
                    case 0:
                        homefragment = new HomeFragment();
                        addFragment(homefragment);
                        break;
                    case 1:
                        homefragment = new ProfileFragment();
                      ///  TabLayout.Tab profileTab = tabLayout.getTabAt(0);
                      //  profileTab.select();
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
            homefragment = new HomeFragment();
            addFragment(homefragment);
            drawerLayout.closeDrawer(Gravity.START,false);
            TabLayout.Tab homeTab = tabLayout.getTabAt(0);
            homeTab.select();
        }
        else if (menuItem.getItemId() == R.id.Logout)
        {
            final AlertDialog.Builder builder = new AlertDialog.Builder(HomepageActivity.this);
            builder.setMessage("Do you want to exit ?");
            builder.setCancelable(true);
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d("Drawer-------->","Logout.....");
                    Toast.makeText(getApplication(),"Logout Successfull",Toast.LENGTH_SHORT).show();
                    clearPref();
                    drawerLayout.closeDrawer(Gravity.START,false);
                    startActivity(new Intent(HomepageActivity.this,LoginActivity.class));
                    finish();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

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

        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.framelayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private void loadFragment(Fragment fragment) {
        // load fragment

        Log.d("LoadFragment-------->","Loading.....");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayout,new ProfileFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public void getId()
    {

        drawerLayout = (DrawerLayout)findViewById(R. id.drawerlayout);
        navigationView = (NavigationView)findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        sharedpreferences = getSharedPreferences(Const.MyPREFERENCES, Context.MODE_PRIVATE);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        View header = navigationView.getHeaderView(0);
        txt_displayname = (TextView)header.findViewById(R.id.txt_displayName);

    }

}
