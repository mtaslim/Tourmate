package com.ityadi.app.tourmate.Weather.WeatherForcats;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by taslim on 8/23/2016.
 */
public class Clouds {

    @SerializedName("all")
    @Expose
    private Integer all;

    /**
     *
     * @return
     * The all
     */
    public Integer getAll() {
        return all;
    }

    /**
     *
     * @param all
     * The all
     */
    public void setAll(Integer all) {
        this.all = all;
    }

}