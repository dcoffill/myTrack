package com.cs48.myTrack;

import android.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Administrator on 14-2-4.
 */
public class MyListFragment extends ListFragment{
    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        super.onListItemClick(l, v, pos, id);
        Toast.makeText(getActivity(), "Location " + (pos+1) + " was loaded", Toast.LENGTH_SHORT).show();
    }

}
