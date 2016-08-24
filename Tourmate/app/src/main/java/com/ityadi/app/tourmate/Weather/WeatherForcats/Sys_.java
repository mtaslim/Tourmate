package com.ityadi.app.tourmate.Weather.WeatherForcats;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by taslim on 8/23/2016.
 */
public class Sys_ {

    @SerializedName("pod")
    @Expose
    private String pod;

    /**
     *
     * @return
     * The pod
     */
    public String getPod() {
        return pod;
    }

    /**
     *
     * @param pod
     * The pod
     */
    public void setPod(String pod) {
        this.pod = pod;
    }

}