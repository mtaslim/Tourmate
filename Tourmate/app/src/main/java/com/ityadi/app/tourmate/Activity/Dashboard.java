package com.ityadi.app.tourmate.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ityadi.app.tourmate.Common.SpreferenceHelper;
import com.ityadi.app.tourmate.R;

public class Dashboard extends AppCompatActivity {
    SpreferenceHelper spreferenceHelper;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        spreferenceHelper = new SpreferenceHelper(this);
        userName = spreferenceHelper.retrive();
    }

    public void sihnOut(View view) {
        spreferenceHelper.clean();
        Intent i = new Intent(getBaseContext(), SigninSignup.class);
        startActivity(i);
    }
}
