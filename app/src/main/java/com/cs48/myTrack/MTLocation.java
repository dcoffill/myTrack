package com.cs48.myTrack;

import android.location.Location;

import java.io.Serializable;

/**
 *This class is responsible for storing attributes of a given recorded location, such as date/time,
 *geographic coordinates, bearing, altitude, and more.  See
 *https://developer.android.com/reference/android/location/Location.html for more information
 */
public class MTLocation extends Location implements Serializable {
	private String description;

	public MTLocation(Location l) {
		super(l);
		description = "";
	}

	public MTLocation(String provider) {
		super(provider);
		description = "";
	}
}
