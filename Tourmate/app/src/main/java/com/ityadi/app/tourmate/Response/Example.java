package com.ityadi.app.tourmate.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by taslim on 8/19/2016.
 */
public class Example {
    @SerializedName("travelEvent")
    @Expose
    private List<TravelEvent> travelEvent = new ArrayList<TravelEvent>();
    public List<TravelEvent> getTravelEvent() {
        return travelEvent;
    }
    public void setTravelEvent(List<TravelEvent> travelEvent) {
        this.travelEvent = travelEvent;
    }
}
