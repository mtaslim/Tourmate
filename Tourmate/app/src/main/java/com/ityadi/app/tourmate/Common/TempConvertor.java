package com.ityadi.app.tourmate.Common;


public class TempConvertor {

    double celsius, fahrenhiet, kelvin;

    public Double getCelcious(double kelvin){
        celsius = kelvin - 273.0;
        return celsius;
    }
    public Double getFarenhite(double kelvin){
        celsius = getCelcious(kelvin);
        fahrenhiet = (celsius * 9.0/5.0) + 32.0;
        return fahrenhiet;
    }
}
