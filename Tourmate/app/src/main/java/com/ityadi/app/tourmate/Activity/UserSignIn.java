package com.ityadi.app.tourmate.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ityadi.app.tourmate.Common.Network;
import com.ityadi.app.tourmate.Common.SpreferenceHelper;
import com.ityadi.app.tourmate.Common.UserLoginApi;
import com.ityadi.app.tourmate.R;
import com.ityadi.app.tourmate.Response.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserSignIn extends AppCompatActivity {
    EditText editTextUsername,editTextPassword;
    String username,password;

    SpreferenceHelper spreferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_sign_in);

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.action_bar_signin);

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        spreferenceHelper = new SpreferenceHelper(this);
    }
    public void SigninSignup(View view) {
        Intent i = new Intent(getBaseContext(), SigninSignup.class);
        startActivity(i);
    }

    public void SignIn(View view) {
        username = editTextUsername.getText().toString();
        password = editTextPassword.getText().toString();

        String req="";
        if("".equals(username)) req+= "Username,";
        if("".equals(password)) req+= "Password,";

        if(req.length()>0) {
            req = req.substring(0,req.length()-1);
            Toast.makeText(getBaseContext(),req+" is required", Toast.LENGTH_LONG).show();
        }
        else{
            UserLoginApi userLoginApi = Network.createService(UserLoginApi.class);
            Call<UserResponse> call = userLoginApi.getAccessToken(username,password);

            call.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    UserResponse userResponse = response.body();
                    String userName = userResponse.getUsername();

                    String msg = userResponse.getMsg();
                    String err = userResponse.getErr();

                    if("1".equals(msg)){
                        spreferenceHelper.save(userName);
                        Intent i = new Intent(getBaseContext(), Dashboard.class);
                        startActivity(i);
                    }
                    else{
                        Toast.makeText(getBaseContext(),err,Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    Log.e("error", t.toString());
                }
            });
        }
    }
}
