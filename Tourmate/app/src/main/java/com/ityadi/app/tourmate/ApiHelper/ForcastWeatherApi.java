package com.ityadi.app.tourmate.ApiHelper;

import com.ityadi.app.tourmate.Weather.WeatherForcats.ForcastWeatherArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by taslim on 8/23/2016.
 */
public interface ForcastWeatherApi {
    @GET("data/2.5/forecast?appid=78910bced6e378938bb4545eaed84406")
    Call<ForcastWeatherArrayList> getWeather(@Query("q") String cities);
}
