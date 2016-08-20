package com.ityadi.app.tourmate.ApiHelper;

import com.ityadi.app.tourmate.Response.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by taslim on 8/19/2016.
 */
public interface TravelEventListApi {
    @GET("tourmate/travelEventList.php?")
    Call<Example>getTravelList(
            @Query("appkey") String appkey,
            @Query("username") String username
    );


}
