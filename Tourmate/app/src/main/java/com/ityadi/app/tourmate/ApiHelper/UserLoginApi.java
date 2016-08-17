package com.ityadi.app.tourmate.ApiHelper;

import com.ityadi.app.tourmate.Response.UserResponse;

import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by taslim on 8/12/2016.
 */
public interface UserLoginApi {
    @Multipart
    @POST("tourmate/userLogin.php")
    Call<UserResponse> getAccessToken(
            @Part("appkey") String appkey,
            @Part("username") String username,
            @Part("password") String password
    );
}
