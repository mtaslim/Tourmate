package com.ityadi.app.tourmate.ApiHelper;

import com.ityadi.app.tourmate.Response.Example;
import com.ityadi.app.tourmate.Response.MomentListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by taslim on 8/25/2016.
 */
public interface MomentListApi {
    @GET("tourmate/momentList.php?")
    Call<MomentListResponse> getMomentList(
            @Query("appkey") String appkey,
            @Query("username") String username
    );
}
