package com.ityadi.app.tourmate.ApiHelper;

import com.ityadi.app.tourmate.Response.UserInfoResponse;

import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserInfoApi {
    @Multipart
    @POST("tourmate/userInfo.php")
    Call<UserInfoResponse> getAccessToken(
            @Part("appkey") String appkey,
            @Part("username") String userName
    );
}

