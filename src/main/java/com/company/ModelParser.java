package com.company;

import org.jsoup.select.Elements;

public class ModelParser {
    private String countryRegion;
    private String dayName;
    private String dayInt;
    private String month;
    private String minTemp;
    private String maxTemp;
    private String humidity;
    private String preasure;
    private String description;
    private String geomag;
    private Elements pngStr;

    public String getGeomag() {
        return geomag;
    }

    public void setGeomag(String geomag) {
        this.geomag = geomag;
    }

    public Elements getPngStr() {
        return pngStr;
    }

    public void setPngStr(Elements pngStr) {
        this.pngStr = pngStr;
    }

    public String getCountryRegion() {
        return countryRegion;
    }

    public void setCountryRegion(String countryRegion) {
        this.countryRegion = countryRegion;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getDayInt() {
        return dayInt;
    }

    public void setDayInt(String dayInt) {
        this.dayInt = dayInt;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPreasure() {
        return preasure;
    }

    public void setPreasure(String preasure) {
        this.preasure = preasure;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
