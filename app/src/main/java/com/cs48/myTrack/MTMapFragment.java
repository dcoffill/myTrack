package com.cs48.myTrack;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by david on 2/4/14.
 */
public class MTMapFragment extends MapFragment {
	private GoogleMap gMap;

	MTMapFragment() {
		super();
	}

	// This is possibly unnecessary, but I'm leaving it because I don't know if we'll need it later
	@Override
	public void onViewCreated(View view, Bundle bundle) {
		super.onViewCreated(view, bundle);
		//myMap = super.getMap();
	}


	@Override
	public void onStart() {
		super.onStart();
		gMap = super.getMap();
	}

	public void onResume() {
		super.onResume();
		// If gMap is currently null, reassign it
		if (this.gMap == null) {
			gMap = this.getMap();
		}

		// Creates a marker
		gMap.addMarker(new MarkerOptions()
			.position(new LatLng(34.3147, -119.8606))
			.title("Isla Vista!"));

		// For now, centers the map above Australia.  Maybe shouldn't be in onResume, since it's
		// annoying how it re-centers every time you even switch apps...
		LatLngBounds australia = new LatLngBounds(new LatLng(-44, 113), new LatLng(-10, 154));
		gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(australia.getCenter(), 3));
	}
}
