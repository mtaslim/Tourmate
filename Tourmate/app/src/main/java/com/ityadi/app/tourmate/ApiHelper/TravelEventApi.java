package com.ityadi.app.tourmate.ApiHelper;

import com.ityadi.app.tourmate.Response.TravelEventResponse;

import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by taslim on 8/17/2016.
 */
public interface TravelEventApi {
    @Multipart
    @POST("tourmate/addTravelEvent.php")
    Call<TravelEventResponse> getAccessToken(
            @Part("appkey") String appkey,
            @Part("username") String userName,
            @Part("event_name") String eventName,
            @Part("location_coverage") String locationCoverage,
            @Part("budget_amount") String budgetAmount,
            @Part("journey_date") String journeyDate,
            @Part("description") String description
    );
}