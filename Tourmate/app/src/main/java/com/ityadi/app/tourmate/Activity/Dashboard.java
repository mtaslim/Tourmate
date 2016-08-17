package com.ityadi.app.tourmate.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ityadi.app.tourmate.ApiHelper.UserInfoApi;
import com.ityadi.app.tourmate.Common.Config;
import com.ityadi.app.tourmate.Common.Network;
import com.ityadi.app.tourmate.Common.SpreferenceHelper;
import com.ityadi.app.tourmate.R;
import com.ityadi.app.tourmate.Response.UserInfoResponse;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SpreferenceHelper spreferenceHelper;
    String userName;

    NavigationView navigationView = null;
    Toolbar toolbar = null;

    String uName = "";
    String uEmail = "";
    String uPhoto = "";
    String uCreated = "";
    String uUpdated = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        spreferenceHelper = new SpreferenceHelper(this);
        userName = spreferenceHelper.retrive();

        //Set the fragment initially
        MainFragment fragment = new MainFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        ///////////// fech user data from database
        UserInfoApi userInfoApi = Network.createService(UserInfoApi.class);
        Call<UserInfoResponse> call = userInfoApi.getAccessToken(Config.APP_KEY,userName);
        call.enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                UserInfoResponse userInfoResponse = response.body();
                uName = userInfoResponse.getName();
                uEmail = userInfoResponse.getEmail();
                uPhoto = userInfoResponse.getPhoto();
                uCreated = userInfoResponse.getCreated_at();
                uUpdated = userInfoResponse.getUpdated_at();

                //How to change elements in the header programatically
                View headerView = navigationView.getHeaderView(0);
                //TextView usernameTV = (TextView) headerView.findViewById(R.id.username);
                TextView nameTV = (TextView) headerView.findViewById(R.id.nameTV);
                CircleImageView profile_image = (CircleImageView) findViewById(R.id.profile_image);

                //usernameTV.setText(uName);
                nameTV.setText(uName);
                String profile_image_url ="http://app.ityadi.com/tourmate/photo/small/"+uPhoto;
                Picasso.with(getApplicationContext()).load(profile_image_url).into(profile_image);

            }
            @Override
            public void onFailure(Call<UserInfoResponse> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });



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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_signout) {
            spreferenceHelper.clean();
            Intent i = new Intent(getBaseContext(), SigninSignup.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_travel_event) {
            MainFragment fragment = new MainFragment();
            FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_nearby) {
            GalleryFragment fragment = new GalleryFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_weather) {

        } else if (id == R.id.nav_my_profile) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void signout() {
        spreferenceHelper.clean();
        Intent i = new Intent(getBaseContext(), SigninSignup.class);
        startActivity(i);
    }
}
