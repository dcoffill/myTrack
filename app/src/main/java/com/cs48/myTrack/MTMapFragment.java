package com.cs48.myTrack;

import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

/**
 * A fragment that builds on top of Google's MapFragment to display an interactive map
 */
public class MTMapFragment extends MapFragment {
	private GoogleMap gMap;
	private static MTMapFragment mMap;
	List<LocationInfo> liList;

	public static MTMapFragment getInstance() {
		if (mMap == null) {
			mMap = new MTMapFragment();
		}
		return mMap;
	}

	private MTMapFragment() {
		super();

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

		// Redraw all the map points
		this.refresh();

		// Manipulate camera position, either to last location, or an arbitrary point if that fails
        try{
            LocationInfo tmpLocation = liList.get(liList.size()-1);
            LatLng cameraCtr = new LatLng(tmpLocation.get_Latitude(),tmpLocation.get_Longitude());
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cameraCtr,17));
        }catch(IndexOutOfBoundsException ex){
                LatLng cameraCtr = new LatLng(34.41,-119.84);
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cameraCtr,11));

        }
	}

	/**
	 * Clear the map and redraw all items (Polylines, Markers, and so on)
	 */
	public void refresh() {
		// Check if gMap is null
		if (gMap == null) {
			Log.e("MTMapFragment", "Unable to refresh, no non-null GoogleMap");
			return;
		}

		// Clear the map
		gMap.clear();

		// Get locations (currently fetches all recorded locations)
		DatabaseHelper dh = new DatabaseHelper(getActivity());
		liList= dh.getAllLocations();

		//create a polyLine and add each marker as points on the line
		PolylineOptions newLine = new PolylineOptions();

		//create the first marker and draw it in
		LocationInfo locationFirst = liList.get(0);
		gMap.addMarker(new MarkerOptions()
				.position(new LatLng(locationFirst.get_Latitude(), locationFirst.get_Longitude()))
				.title("Location #1 - Start Point")
				.snippet("\""+ locationFirst.get_Description()+"\"")
				.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
		newLine.add(new LatLng(locationFirst.get_Latitude(),locationFirst.get_Longitude()));

		//draw all markers left in RED except the last one
		int j = 1;
		for (LocationInfo location: (liList.subList(1,(liList.size()-1)))) {
			gMap.addMarker(new MarkerOptions()
					.position(new LatLng(location.get_Latitude(), location.get_Longitude()))
					.title("Location #" + (j + 1))
					.snippet("\""+ location.get_Description()+"\""))
					.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
			newLine.add(new LatLng(location.get_Latitude(), location.get_Longitude()));
			++j;
		}

		//create the last marker and draw it in AZURE
		LocationInfo locationLast = liList.get(liList.size()-1);
		gMap.addMarker(new MarkerOptions()
				.position(new LatLng(locationLast.get_Latitude(), locationLast.get_Longitude()))
				.title("Location #" + liList.size()+" - Latest Point")
				.snippet("\""+ locationLast.get_Description()+"\"")
				.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
		newLine.add(new LatLng(locationLast.get_Latitude(),locationLast.get_Longitude()));

		//draw the polyLine on map
		gMap.addPolyline(newLine);
	}
}

