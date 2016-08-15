package com.ityadi.app.tourmate.Common;

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
            @Part("username") String username,
            @Part("password") String password
    );
}
