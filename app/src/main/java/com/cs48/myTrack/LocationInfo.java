package com.cs48.myTrack;

import android.location.Location;

/**
 * A class that stores information about a given location.  Currently used to interface with the
 * DatabaseHelper
 */
public class LocationInfo {

    //private variables
    private int _id;
    private Double _latitude;
    private Double _longitude;

    // Empty constructor
    public LocationInfo(){

    }
    // constructor
    public LocationInfo(int id, Double latitude, Double longitude){
        this._id = id;
        this._latitude = latitude;
        this._longitude = longitude;
    }

    // constructor
    public LocationInfo(Double latitude, Double longitude){
        this._latitude = latitude;
        this._longitude = longitude;
    }

    // constructor
    public LocationInfo(Location location){
        this._latitude = location.getLatitude();
        this._longitude = location.getLongitude();
    }
    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
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