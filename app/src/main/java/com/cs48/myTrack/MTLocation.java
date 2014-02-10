package com.cs48.myTrack;

import android.location.Location;

import java.io.Serializable;

/**
 * Created by david on 2/9/14.
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
