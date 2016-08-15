package com.ityadi.app.tourmate.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ityadi.app.tourmate.Common.SpreferenceHelper;
import com.ityadi.app.tourmate.R;

public class SigninSignup extends AppCompatActivity {
    SpreferenceHelper spreferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_signup);


        spreferenceHelper = new SpreferenceHelper(this);
        String userName = spreferenceHelper.retrive();

        if(!"".equals(userName)) {
            Intent i = new Intent(getBaseContext(), Dashboard.class);
            startActivity(i);
        }
    }

    public void SignIn(View view) {
        Intent i = new Intent(getBaseContext(), UserSignIn.class);
        startActivity(i);
    }

    public void SignUp(View view) {
        Intent i = new Intent(getBaseContext(), UserAccountCreation.class);
        startActivity(i);
    }




}
