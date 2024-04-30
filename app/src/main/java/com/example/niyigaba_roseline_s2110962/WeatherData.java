package com.example.niyigaba_roseline_s2110962;

public class WeatherData {
    private String cityName;
    private String mainTemp;
    private String mainIcon;
    private String humidity;
    private String pressure;
    private String windDirection;
    private String windSpeed;
    private String visibility;
    private String uvRisk;
    private String pollution;
    private String sunrise;
    private String sunset;
    private String[] dayNames;
    private String[] dayMinTemps;
    private String[] dayMaxTemps;
    private String[] dayIcons;
    private String[] dayConditions;

    public WeatherData(String cityName, String mainTemp, String mainIcon, String[] dayNames, String[] dayMinTemps, String[] dayMaxTemps, String[] dayIcons, String humidity, String pressure, String windDirection, String windSpeed, String visibility, String uvRisk, String pollution, String sunrise, String sunset) {
        this.cityName = cityName;
        this.mainTemp = mainTemp;
        this.mainIcon = mainIcon;
        this.dayNames = dayNames;
        this.dayMinTemps = dayMinTemps;
        this.dayMaxTemps = dayMaxTemps;
        this.dayIcons = dayIcons;
        this.dayConditions = new String[3];
        this.humidity = humidity;
        this.pressure = pressure;
        this.windDirection = windDirection;
        this.windSpeed = windSpeed;
        this.visibility = visibility;
        this.uvRisk = uvRisk;
        this.pollution = pollution;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    // Getters and setters for all properties

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getMainTemp() {
        return mainTemp;
    }

    public void setMainTemp(String mainTemp) {
        this.mainTemp = mainTemp;
    }

    public String getMainIcon() {
        return mainIcon;
    }

    public void setMainIcon(String mainIcon) {
        this.mainIcon = mainIcon;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getUvRisk() {
        return uvRisk;
    }

    public void setUvRisk(String uvRisk) {
        this.uvRisk = uvRisk;
    }

    public String getPollution() {
        return pollution;
    }

    public void setPollution(String pollution) {
        this.pollution = pollution;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String[] getDayNames() {
        return dayNames;
    }

    public void setDayNames(String[] dayNames) {
        this.dayNames = dayNames;
    }

    public String[] getDayMinTemps() {
        return dayMinTemps;
    }

    public void setDayMinTemps(String[] dayMinTemps) {
        this.dayMinTemps = dayMinTemps;
    }

    public String[] getDayMaxTemps() {
        return dayMaxTemps;
    }

    public void setDayMaxTemps(String[] dayMaxTemps) {
        this.dayMaxTemps = dayMaxTemps;
    }

    public String[] getDayIcons() {
        return dayIcons;
    }

    public void setDayIcons(String[] dayIcons) {
        this.dayIcons = dayIcons;
    }

    public String[] getDayConditions() {
        return dayConditions;
    }

    public void setDayConditions(String[] dayConditions) {
        this.dayConditions = dayConditions;
    }
}