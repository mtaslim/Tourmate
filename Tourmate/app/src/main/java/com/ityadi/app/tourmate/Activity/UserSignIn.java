package com.ityadi.app.tourmate.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.ityadi.app.tourmate.Common.Config;
import com.ityadi.app.tourmate.Common.InternetConnection;
import com.ityadi.app.tourmate.Common.Network;
import com.ityadi.app.tourmate.Common.SpreferenceHelper;
import com.ityadi.app.tourmate.ApiHelper.UserLoginApi;
import com.ityadi.app.tourmate.R;
import com.ityadi.app.tourmate.Response.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserSignIn extends AppCompatActivity {
    EditText editTextUsername,editTextPassword;
    String username,password;

    SpreferenceHelper spreferenceHelper;
    View signinLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_sign_in);

        spreferenceHelper = new SpreferenceHelper(this);
        String userName = spreferenceHelper.retrive();

        if(!"".equals(userName)) {
            Intent i = new Intent(getBaseContext(), Dashboard.class);
            startActivity(i);
        }

       // getSupportActionBar().setDisplayShowCustomEnabled(true);
       // getSupportActionBar().setCustomView(R.layout.action_bar_signin);

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        signinLayout = findViewById(R.id.signinLayout);
    }

    public void SignIn(View view) {
        if (InternetConnection.checkConnection(getBaseContext())) {

            username = editTextUsername.getText().toString();
            password = editTextPassword.getText().toString();

            String req="";
            if("".equals(username)) req+= "Username,";
            if("".equals(password)) req+= "Password,";

            if(req.length()>0) {
                req = req.substring(0,req.length()-1);
                Snackbar.make(signinLayout,req+" is required", Snackbar.LENGTH_LONG).show();
            }
            else{
                UserLoginApi userLoginApi = Network.createService(UserLoginApi.class);
                Call<UserResponse> call = userLoginApi.getAccessToken(Config.APP_KEY,username,password);

                call.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        UserResponse userResponse = response.body();
                        if(userResponse == null){
                            Snackbar.make(signinLayout,"Server not responding. Please try later.", Snackbar.LENGTH_LONG).show();
                        }
                       else {
                            String userName = userResponse.getUsername();
                            String msg = userResponse.getMsg();
                            String err = userResponse.getErr();

                            if ("1".equals(msg)) {
                                spreferenceHelper.save(userName);
                                Intent i = new Intent(getBaseContext(), Dashboard.class);
                                startActivity(i);
                            } else {
                                Snackbar.make(signinLayout, err, Snackbar.LENGTH_LONG).show();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Log.e("error", t.toString());
                    }
                });
            }

        }
         else {
            Snackbar.make(signinLayout,"Internet connection is not available.", Snackbar.LENGTH_LONG).show();
        }

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }


    public void SignUp(View view) {
        if (InternetConnection.checkConnection(getBaseContext())) {
            Intent i = new Intent(getBaseContext(), UserAccountCreation.class);
            startActivity(i);
        } else {
            Snackbar.make(signinLayout,"Internet connection is not available.", Snackbar.LENGTH_LONG).show();
        }
    }

}


