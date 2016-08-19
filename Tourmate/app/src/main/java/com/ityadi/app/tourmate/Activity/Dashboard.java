package com.ityadi.app.tourmate.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.ityadi.app.tourmate.ApiHelper.TravelEventApi;
import com.ityadi.app.tourmate.ApiHelper.UserInfoApi;
import com.ityadi.app.tourmate.Common.Config;
import com.ityadi.app.tourmate.Common.InternetConnection;
import com.ityadi.app.tourmate.Common.InternetConnectionHandler;
import com.ityadi.app.tourmate.Common.Network;
import com.ityadi.app.tourmate.Common.SpreferenceHelper;
import com.ityadi.app.tourmate.R;
import com.ityadi.app.tourmate.Response.TravelEventResponse;
import com.ityadi.app.tourmate.Response.UserInfoResponse;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    SpreferenceHelper spreferenceHelper;

    NavigationView navigationView = null;
    Toolbar toolbar = null;
    FloatingActionButton fab;
    View thisLayout;

    String userName;
    String uName = "";
    String uEmail = "";
    String uPhoto = "";
    String uCreated = "";
    String uUpdated = "";

    String CUR_DATE = "";
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);


        if (InternetConnection.checkConnection(getBaseContext())) {
            fab = (FloatingActionButton) findViewById(R.id.fab);
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();
            navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            //Set the fragment initially
            TravelEventListFragment fragment = new TravelEventListFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

            spreferenceHelper = new SpreferenceHelper(this);
            userName = spreferenceHelper.retrive();

            ///////////// fech user data from database
            UserInfoApi userInfoApi = Network.createService(UserInfoApi.class);
            Call<UserInfoResponse> call = userInfoApi.getAccessToken(Config.APP_KEY, userName);
            call.enqueue(new Callback<UserInfoResponse>() {
                @Override
                public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                    UserInfoResponse userInfoResponse = response.body();
                    uName = userInfoResponse.getName();
                    uEmail = userInfoResponse.getEmail();
                    uPhoto = userInfoResponse.getPhoto();
                    uCreated = userInfoResponse.getCreated_at();
                    uUpdated = userInfoResponse.getUpdated_at();

                    //Change elements in the header
                    View headerView = navigationView.getHeaderView(0);
                    TextView nameTV = (TextView) headerView.findViewById(R.id.nameTV);
                    CircleImageView profile_image = (CircleImageView) findViewById(R.id.profile_image);

                    nameTV.setText(uName);
                    String profile_image_url = "http://app.ityadi.com/tourmate/photo/small/" + uPhoto;
                    Picasso.with(getApplicationContext()).load(profile_image_url).into(profile_image);
                }

                @Override
                public void onFailure(Call<UserInfoResponse> call, Throwable t) {
                    Log.e("error", t.toString());
                }
            });

            Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        }
        else {
            startActivity(new Intent(getBaseContext(), InternetConnectionHandler.class));
        }
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
            Intent i = new Intent(getBaseContext(), UserSignIn.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_travel_list_event) {
            TravelEventListFragment fragment = new TravelEventListFragment();
            FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        }

        else if (id == R.id.nav_nearby) {
            GalleryFragment fragment = new GalleryFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_weather) {
            //Intent i = new Intent(getBaseContext(), UserAccountCreation.class);
            // startActivity(i);

        } else if (id == R.id.nav_my_profile) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @SuppressWarnings("deprecation")
    public void setDate() {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    public DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            String day,month,year="-"+arg1;
            int arg=arg2+1;

            if(arg3<10) day="0"+arg3;
            else day=""+arg3;

            if(arg<10) month="-0"+arg;
            else month="-"+arg;

            CUR_DATE = day+month+year;
        }
    };

    public void CreateTravelEvent(View view) {
        if (InternetConnection.checkConnection(getBaseContext())) {
            thisLayout = findViewById(R.id.travel_event_fragment);
            EditText etEventName = (EditText) findViewById(R.id.etEventName);
            EditText etLocationCoverage = (EditText) findViewById(R.id.etLocationCoverage);
            EditText etBudgetAmount = (EditText) findViewById(R.id.etBudgetAmount);
            EditText etDescription = (EditText) findViewById(R.id.etDescription);
            TextView tvDate = (TextView) findViewById(R.id.tvDate);

            String eventName, locationCoverage, budgetAmount, journeyDate, description;

            eventName = etEventName.getText().toString();
            locationCoverage = etLocationCoverage.getText().toString();
            budgetAmount = etBudgetAmount.getText().toString();
            description = etDescription.getText().toString();
            journeyDate = tvDate.getText().toString();

            String req = "";
            if ("".equals(eventName)) req += "Event Name, ";
            if ("".equals(journeyDate)) req += "Journey Date, ";
            if ("".equals(locationCoverage)) req += "Location Coverage, ";
            if ("".equals(budgetAmount)) req += "Budget Amount, ";
            if ("".equals(description)) req += "Description, ";

            if (req.length() > 0) {
                req = req.substring(0, req.length() - 2) + " is required";
                Snackbar.make(thisLayout, req, Snackbar.LENGTH_LONG).show();
            } else {
                final ProgressDialog progressDialog;
                progressDialog = new ProgressDialog(Dashboard.this);
                progressDialog.setMessage("Please wait. Your data is storing...");
                progressDialog.show();

                TravelEventApi travelEventApi = Network.createService(TravelEventApi.class);
                Call<TravelEventResponse> call = travelEventApi.getAccessToken(Config.APP_KEY, userName, eventName, locationCoverage, budgetAmount, journeyDate, description);

                call.enqueue(new Callback<TravelEventResponse>() {
                    @Override
                    public void onResponse(Call<TravelEventResponse> call, Response<TravelEventResponse> response) {
                        progressDialog.dismiss();
                        TravelEventResponse travelEventResponse = response.body();
                        String userName = travelEventResponse.getEventName();
                        String msg = travelEventResponse.getMsg();
                        String err = travelEventResponse.getErr();
                        Snackbar.make(thisLayout, msg, Snackbar.LENGTH_LONG).show();

                        if (!"".equals(msg)) {
                            Snackbar.make(thisLayout, msg, Snackbar.LENGTH_LONG).show();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent i = new Intent(getBaseContext(), Dashboard.class);
                                    startActivity(i);
                                }
                            }, 5000);


                        } else Snackbar.make(thisLayout, err, Snackbar.LENGTH_LONG).show();
                        //Log.e("response", userResponse.getMsg());
                    }

                    @Override
                    public void onFailure(Call<TravelEventResponse> call, Throwable t) {
                        Log.e("error", t.toString());
                    }
                });
            }
        }
        else{
            startActivity(new Intent(getBaseContext(), InternetConnectionHandler.class));
        }
    }

}
