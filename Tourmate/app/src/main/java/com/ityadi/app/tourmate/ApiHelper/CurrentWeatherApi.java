package com.ityadi.app.tourmate.ApiHelper;

import com.ityadi.app.tourmate.Weather.CurrentWeatherArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by taslim on 8/23/2016.
 */
public interface CurrentWeatherApi {
    @GET("data/2.5/weather?appid=78910bced6e378938bb4545eaed84406")
    Call<CurrentWeatherArrayList> getWeather(@Query("q") String cities);
}
