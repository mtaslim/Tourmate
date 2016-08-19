package com.ityadi.app.tourmate.Common;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.ityadi.app.tourmate.Activity.Dashboard;
import com.ityadi.app.tourmate.R;

public class InternetConnectionHandler extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.internet_connection_handler);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (InternetConnection.checkConnection(getBaseContext())) {
                    Intent i = new Intent(getBaseContext(), Dashboard.class);
                    startActivity(i);
                }
                else{
                    Intent i = new Intent(getBaseContext(), TryingToConnectInternet.class);
                    startActivity(i);
                }
                finish();
            }
        }, 10000);


    }

}
