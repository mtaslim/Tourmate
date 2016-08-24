package com.ityadi.app.tourmate.Weather.WeatherForcats;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by taslim on 8/23/2016.
 */
public class Sys {

    @SerializedName("population")
    @Expose
    private Integer population;

    /**
     *
     * @return
     * The population
     */
    public Integer getPopulation() {
        return population;
    }

    /**
     *
     * @param population
     * The population
     */
    public void setPopulation(Integer population) {
        this.population = population;
    }

}
