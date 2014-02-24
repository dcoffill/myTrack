package com.cs48.myTrack;

import android.app.FragmentManager;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

/**
 * A simple fragment that displays locations in a list format
 */
public class MTListFragment extends ListFragment{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        //super.onListItemClick(l, v, pos, id);
        //Toast.makeText(getActivity(), "Please add location description", Toast.LENGTH_SHORT).show();

        FragmentManager mManager = getFragmentManager();
        MTPopupDialogFragment mPopupDialogFragment = new MTPopupDialogFragment();
        mPopupDialogFragment.show(mManager,"popupDialog");

    }

}


