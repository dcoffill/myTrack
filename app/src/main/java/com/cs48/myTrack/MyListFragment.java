package com.cs48.myTrack;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

/**
 * A simple fragment that displays locations in a list format
 */
public class MyListFragment extends ListFragment{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        super.onListItemClick(l, v, pos, id);
        Toast.makeText(getActivity(), "Location loaded successfully", Toast.LENGTH_SHORT).show();
    }

}
