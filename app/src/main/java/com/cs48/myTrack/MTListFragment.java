package com.cs48.myTrack;

import android.app.ListFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

/**
 * Created by Wilson on 14-2-4.
 */
public class MTListFragment extends ListFragment{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        //super.onListItemClick(l, v, pos, id);
        Toast.makeText(getActivity(), "Please add location description", Toast.LENGTH_SHORT).show();

        View view = getActivity().getLayoutInflater().inflate(R.layout.popup, null);

        final PopupWindow popupWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
        Button btnCancel = (Button)view.findViewById(R.id.cancel);
        btnCancel.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                try { Thread.sleep(50); } catch (InterruptedException ex) { }
                popupWindow.dismiss();
            }});

        Button btnAdd = (Button)view.findViewById(R.id.add);
        btnAdd.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                try { Thread.sleep(50); } catch (InterruptedException ex) { }
                popupWindow.dismiss();
            }});
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        
        // Get the View's(the one that was clicked in the Fragment) location
        int location[] = new int[2];
        v.getLocationOnScreen(location);

        // Using location, the PopupWindow will be displayed right under anchorView
        popupWindow.showAtLocation(v, Gravity.NO_GRAVITY,
                location[0], location[1] + v.getHeight());




    }

}


