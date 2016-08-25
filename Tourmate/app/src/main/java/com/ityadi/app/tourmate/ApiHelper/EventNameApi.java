package com.ityadi.app.tourmate.ApiHelper;

import com.ityadi.app.tourmate.Response.EventNameResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by taslim on 8/25/2016.
 */
public interface EventNameApi {
    @GET("tourmate/eventName.php?")
    Call<EventNameResponse> getEventName(
            @Query("appkey") String appkey,
            @Query("event_id") String event_id
    );
}
