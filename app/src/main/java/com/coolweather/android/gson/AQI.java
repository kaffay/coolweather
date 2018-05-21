package com.coolweather.android.gson;

/**
 * Created by kaff on 2018-05-21.
 */

public class AQI {
    public AQICity city;
    public class AQICity {
        public String aqi;
        public String pm25;
    }
}
