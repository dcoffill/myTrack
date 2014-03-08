package com.cs48.myTrack;

import android.location.Location;

/**
 * A class that stores information about a given location.  Currently used to interface with the
 * DatabaseHelper
 */
public class LocationInfo {

    //private variables
    private long _time;
    private Double _latitude;
    private Double _longitude;
    private String _description;

    //These constructor will cause potential error since we are using time as unique key, be carefully and know how time is set in each constructor

    // Empty constructor
    public LocationInfo(){

    }
    // constructor
    public LocationInfo(long time, Double latitude, Double longitude, String description){
        this._time = time;
        this._latitude = latitude;
        this._longitude = longitude;
        this._description = description;
    }

    // constructor
    public LocationInfo(Double latitude, Double longitude){
        this._latitude = latitude;
        this._longitude = longitude;
    }

    // constructor
    public LocationInfo(Location location){
        this._time = location.getTime();
        this._latitude = location.getLatitude();
        this._longitude = location.getLongitude();
    }
    // getting Time
    public long getTime(){
        return this._time;
    }

    // setting time
    public void setTime(long time){
        this._time = time;
    }

    // getting Description
    public String get_Description(){
        return this._description;
    }

    // setting Description
    public void set_Description(String d){
        this._description = d;
    }

    // getting latitude
    public Double get_Latitude(){
        return this._latitude;
    }

    // setting latitude
    public void set_Latitude(Double latitude){
        this._latitude = latitude;
    }

    // getting longitude
    public Double get_Longitude(){
        return this._longitude;
    }

    // setting phone number
    public void set_Longitude(Double longitude){
        this._longitude = longitude;
    }
}