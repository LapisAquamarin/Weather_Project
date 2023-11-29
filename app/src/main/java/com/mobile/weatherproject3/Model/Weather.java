package com.mobile.weatherproject3.Model;

public class Weather {
    String cityName, weather, tempature, conditionDetail, time;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTempature() {
        return tempature;
    }

    public void setTempature(String tempature) {
        this.tempature = tempature;
    }

    public String getConditionDetail() {
        return conditionDetail;
    }

    public void setConditionDetail(String conditionDetail) {
        this.conditionDetail = conditionDetail;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Weather(String cityName, String weather, String tempature, String conditionDetail, String time) {
        this.cityName = cityName;
        this.weather = weather;
        this.tempature = tempature;
        this.conditionDetail = conditionDetail;
        this.time = time;
    }

    public Weather() {
    }
}
