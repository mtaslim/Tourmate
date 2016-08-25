package com.ityadi.app.tourmate.ApiHelper;

import com.ityadi.app.tourmate.Response.TotalExpenseResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by taslim on 8/25/2016.
 */
public interface TotalExpenseApi {
    @GET("tourmate/totalExpense.php")
    Call<TotalExpenseResponse> getAccessToken(
            @Query("appkey") String appkey,
            @Query("event_id") String eventId
    );
}
