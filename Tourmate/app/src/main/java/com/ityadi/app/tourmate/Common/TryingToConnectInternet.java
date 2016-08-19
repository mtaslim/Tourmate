package com.ityadi.app.tourmate.Common;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.ityadi.app.tourmate.Activity.UserSignIn;
import com.ityadi.app.tourmate.R;

public class TryingToConnectInternet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trying_to_connect_internet);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getBaseContext(), UserSignIn.class);
                startActivity(i);
                finish();
            }
        }, 2000);
    }
}
