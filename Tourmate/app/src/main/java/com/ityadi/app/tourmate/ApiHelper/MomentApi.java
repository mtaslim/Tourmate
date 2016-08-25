package com.ityadi.app.tourmate.ApiHelper;

import com.ityadi.app.tourmate.Response.MomentResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by taslim on 8/24/2016.
 */
public interface MomentApi {
    @Multipart
    @POST("tourmate/moment.php")
    Call<MomentResponse> getAccessToken(
            @Part("appkey") String appkey,
            @Part("event_id") String event_id,
            @Part("heading") String heading,
            @Part("expenseAmount") String expenseAmount,
            @Part("description") String description,
            @Part MultipartBody.Part file
    );
}