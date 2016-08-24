package com.ityadi.app.tourmate.Weather.WeatherForcats;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by taslim on 8/23/2016.
 */
public class ForcastWeatherArrayList {

    @SerializedName("city")
    @Expose
    private City city;
    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("message")
    @Expose
    private Double message;
    @SerializedName("cnt")
    @Expose
    private Integer cnt;
    @SerializedName("list")
    @Expose
    private java.util.List<com.ityadi.app.tourmate.Weather.WeatherForcats.List> list = new ArrayList<List>();


    public City getCity() {
        return city;
    }
    public void setCity(City city) {
        this.city = city;
    }
    public String getCod() {
        return cod;
    }
    public void setCod(String cod) {
        this.cod = cod;
    }
    public Double getMessage() {
        return message;
    }
    public void setMessage(Double message) {
        this.message = message;
    }
    public Integer getCnt() {
        return cnt;
    }
    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public java.util.List<com.ityadi.app.tourmate.Weather.WeatherForcats.List> getList() {
        return list;
    }
    public void setList(java.util.List<com.ityadi.app.tourmate.Weather.WeatherForcats.List> list) {
        this.list = list;
    }
    

}