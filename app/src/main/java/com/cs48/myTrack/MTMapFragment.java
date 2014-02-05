package com.cs48.myTrack;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

/**
 * Created by david on 2/4/14.
 */
public class MTMapFragment extends MapFragment {
	public GoogleMap myMap;

	MTMapFragment() {
		super();
	}

	@Override
	public void onViewCreated(View view, Bundle bundle) {
		super.onViewCreated(view, bundle);
		//myMap = super.getMap();
	}

	@Override
	public void onStart() {
		super.onStart();
		myMap = super.getMap();
	}



}
