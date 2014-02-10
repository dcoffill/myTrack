package com.cs48.myTrack;

import android.location.Location;
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
	private LocationList list = new LocationList();

	MTMapFragment() {
		super();
		MTLocation l1 = new MTLocation("1");
		l1.setLatitude(10);
		l1.setLongitude(-10);

		MTLocation l2 = new MTLocation("2");
		l2.setLatitude(10);
		l2.setLongitude(10);

		MTLocation l3 = new MTLocation("3");
		l3.setLatitude(10);
		l3.setLongitude(50);
		list.add(l1);
		list.add(l2);
		list.add(l3);


	}

	// This is possibly unnecessary, but I'm leaving it because I don't know if we'll need it later
//	@Override
//	public void onViewCreated(View view, Bundle bundle) {
//		super.onViewCreated(view, bundle);
//		//myMap = super.getMap();
//	}

//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//
//	}

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

		for (MTLocation location: list) {
			gMap.addMarker(new MarkerOptions()
			.position(new LatLng(location.getLatitude(), location.getLongitude()))
			.title(location.getProvider()));
		}

//		// For now, centers the map above Australia.  Maybe shouldn't be in onResume, since it's
//		// annoying how it re-centers every time you even switch apps...
//		gMap = super.getMap();
//		LatLngBounds australia = new LatLngBounds(new LatLng(-44, 113), new LatLng(-10, 154));
//		gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(australia.getCenter(), 3));

	}
}
