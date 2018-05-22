package com.coolweather.android.util;

import android.text.TextUtils;
import android.util.Log;

import com.coolweather.android.db.City;
import com.coolweather.android.db.Country;
import com.coolweather.android.db.Province;
import com.coolweather.android.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kaff on 2018-05-18.
 */

public class Utility {
    public static boolean handleProvinceResponse(String response) {
        if (TextUtils.isEmpty(response)) return false;

        try {
            JSONArray allProvince = new JSONArray(response);
            for (int i = 0; i < allProvince.length(); i++) {
                JSONObject provinceObject = allProvince.getJSONObject(i);
                Province province = new Province();
                province.setProvinceName(provinceObject.getString("name"));
                province.setProvinceCode(provinceObject.getInt("id"));
                province.save();
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static final String TAG = "Utility";
    public static boolean handleCityResponse(String response, int provinceId) {
        if (TextUtils.isEmpty(response)) return false;
        try {
            Log.e(TAG, "handleCityResponse: " + response);

            JSONArray allCities = new JSONArray(response);
            for (int i = 0; i < allCities.length(); i++) {
                JSONObject cityObject = allCities.getJSONObject(i);
                City city = new City();
                city.setProvinceId(provinceId);
                city.setCityCode(cityObject.getInt("id"));
                city.setCityName(cityObject.getString("name"));
                city.save();
            }
            return  true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean handleCountryResponse(String response, int cityId) {
        if (TextUtils.isEmpty(response)) return false;
        try {
            JSONArray allCountries = new JSONArray(response);
            for (int i = 0; i < allCountries.length(); i++) {
                JSONObject countryObject = allCountries.getJSONObject(i);
                Country country = new Country();
                country.setCityId(cityId);
                country.setCountryName(countryObject.getString("name"));
                country.setWeatherId(countryObject.getString("weather_id"));
                country.save();
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Weather handleWeatherResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent, Weather.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
