package com.ityadi.app.tourmate.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ityadi.app.tourmate.R;

public class SplashScreen extends Activity {
    private static int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getBaseContext(), SigninSignup.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
