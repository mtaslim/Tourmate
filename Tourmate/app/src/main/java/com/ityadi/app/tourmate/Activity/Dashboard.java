package com.ityadi.app.tourmate.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ityadi.app.tourmate.ApiHelper.TravelEventApi;
import com.ityadi.app.tourmate.ApiHelper.TravelEventUpdateApi;
import com.ityadi.app.tourmate.ApiHelper.UserInfoApi;
import com.ityadi.app.tourmate.Common.Config;
import com.ityadi.app.tourmate.Common.InternetConnection;
import com.ityadi.app.tourmate.Common.InternetConnectionHandler;
import com.ityadi.app.tourmate.Common.Network;
import com.ityadi.app.tourmate.Common.SpreferenceHelper;
import com.ityadi.app.tourmate.PhotoLibrary.PhotoLibrary;
import com.ityadi.app.tourmate.R;
import com.ityadi.app.tourmate.Response.TravelEventResponse;
import com.ityadi.app.tourmate.Response.UserInfoResponse;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
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
    PhotoLibrary photoLibrary;
    public Uri fileUri;
    public String timeStamp,realPath="";
    ImageView photoView;


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

                    if("".equals(userInfoResponse.getName())){
                        startActivity(new Intent(getBaseContext(), InternetConnectionHandler.class));
                    }
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


                    if("".equals(uPhoto)) {
                       // uPhoto =
                    }
                    else {
                        String profile_image_url = Config.BASE_URL+"tourmate/photo/small/" + uPhoto;
                        Picasso.with(getApplicationContext()).load(profile_image_url).into(profile_image);
                    }
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
        photoLibrary = new PhotoLibrary();
    }

    @Override
    public void onBackPressed() {
        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
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


    public void travelEventAddEdit(int travel_event_id){
        if (InternetConnection.checkConnection(getBaseContext())) {

            TravelEventApi travelEventApi;
            TravelEventUpdateApi travelEventUpdateApi;
            Call<TravelEventResponse> call;

            if(travel_event_id == 0) thisLayout = findViewById(R.id.travel_event_fragment);
            else thisLayout = findViewById(R.id.fragment_travel_event_details);

            EditText etEventName = (EditText) findViewById(R.id.etEventName);
            EditText etLocationCoverage = (EditText) findViewById(R.id.etLocationCoverage);
            EditText etBudgetAmount = (EditText) findViewById(R.id.etBudgetAmount);
            EditText etDescription = (EditText) findViewById(R.id.etDescription);
            TextView etJourneyDate = (TextView) findViewById(R.id.etJourneyDate);

            String eventName, locationCoverage, budgetAmount, journeyDate, description;

            eventName = etEventName.getText().toString();
            locationCoverage = etLocationCoverage.getText().toString();
            budgetAmount = etBudgetAmount.getText().toString();
            description = etDescription.getText().toString();
            journeyDate = etJourneyDate.getText().toString();

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

                if(travel_event_id <= 0){
                    travelEventApi = Network.createService(TravelEventApi.class); // add
                    call = travelEventApi.getAccessToken(Config.APP_KEY, userName,eventName, locationCoverage, budgetAmount, journeyDate, description);
                }
                else{
                    travelEventUpdateApi = Network.createService(TravelEventUpdateApi.class); //update
                    call = travelEventUpdateApi.getAccessToken(Config.APP_KEY, userName,travel_event_id, eventName, locationCoverage, journeyDate, description);
                }

                call.enqueue(new Callback<TravelEventResponse>() {
                    @Override
                    public void onResponse(Call<TravelEventResponse> call, Response<TravelEventResponse> response) {
                        progressDialog.dismiss();
                        TravelEventResponse travelEventResponse = response.body();
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
                            }, 2000);


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

    public void CreateTravelEvent(View view) {
        travelEventAddEdit(0);
    }

    public void updateTravelEvent(View view) {
        TravelEventDetails travelEventDetails = new TravelEventDetails();
        int id = Integer.parseInt(travelEventDetails.EVENT_ID);
        if(id !=0) travelEventAddEdit(id);
        else   Toast.makeText(getApplicationContext(),"Information did not match", Toast.LENGTH_LONG).show();
    }

    public void captureImage(View view) {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = Uri.fromFile(photoLibrary.getOutputPhotoFile());
        i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(i, photoLibrary.RESULT_CAPTURE_IMAGE );
    }

    public void selectImage(View view) {
        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, photoLibrary.RESULT_LOAD_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if (requestCode == photoLibrary.RESULT_LOAD_IMAGE) {
                if(data != null) {
                    Uri selectedImage = data.getData();
                    Bitmap bmp = null;
                    try {
                        bmp = photoLibrary.getBitmapFromUri(getBaseContext(),selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //photoView.setImageBitmap(bmp);
                    Uri uri = photoLibrary.getImageUri(getBaseContext(), bmp);
                    realPath =  photoLibrary.getRealPathFromURI(getBaseContext(),uri);
                } // if(data != null)
            }
            else if (requestCode == photoLibrary.RESULT_CAPTURE_IMAGE){
                realPath = String.valueOf(photoLibrary.getOutputPhotoFile());
                File file = new File(realPath);
                if(file.exists()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    //photoView.setImageBitmap(myBitmap);
                }
            }
        }


    }




    /*public void eventLocationWeather(View view) {
        TravelEventDetails travelEventDetails = new TravelEventDetails();
        String eventLocation = travelEventDetails.EVENT_LOCATION;

        if("".equals(eventLocation)){
            Toast.makeText(getApplicationContext(),"Location did not found.", Toast.LENGTH_LONG).show();
        }
        else {
            //Task Here
            //Toast.makeText(getApplicationContext(),eventLocation, Toast.LENGTH_LONG).show();
        }
    }*/
}
