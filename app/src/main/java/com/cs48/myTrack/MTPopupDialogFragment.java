package com.cs48.myTrack;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MTPopupDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //Create the EditText View for user to add description
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View addDscrp = inflater.inflate(R.layout.popup, null);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llp.setMargins(50, 10, 0, 0); // llp.setMargins(left, top, right, bottom);
        addDscrp.setBackgroundColor(Color.parseColor("#FFF5F5F5"));
        addDscrp.setLayoutParams(llp);

        //create two description textViews(title and content) that display current description
        LinearLayout.LayoutParams llp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llp1.setMargins(40, 13, 40, 0); // llp.setMargins(left, top, right, bottom);

        TextView currentDscrpTitle = new TextView(getActivity());
        String tmpDscrpTitle = "Current Description for #"+ListDialogTransactor.itemNum+":";
        currentDscrpTitle.setText(tmpDscrpTitle);
        currentDscrpTitle.setTextColor(Color.parseColor("#FF20B2AA"));//LightSeaGreen
        currentDscrpTitle.setLayoutParams(llp1);
        currentDscrpTitle.setTextSize(20);

        LinearLayout.LayoutParams llp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llp2.setMargins(53, 13, 40, 0); // llp.setMargins(left, top, right, bottom);
        TextView currentDscrpContent = new TextView(getActivity());
        String tmpDscrpContent = "\""+(ListDialogTransactor.description)+"\"";
        currentDscrpContent.setText(tmpDscrpContent);
        currentDscrpContent.setTextColor(Color.parseColor("#FF696969"));//DimGray
        currentDscrpContent.setLayoutParams(llp2);
        currentDscrpContent.setHorizontallyScrolling(false);
        currentDscrpTitle.setTextSize(19);


        LinearLayout.LayoutParams llp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llp3.setMargins(0, 0, 0, 20); // llp.setMargins(left, top, right, bottom);
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(llp3);
        linearLayout.addView(currentDscrpTitle);
        linearLayout.addView(currentDscrpContent);
        linearLayout.addView(addDscrp);


        //create a DatabaseHelper to be used
        final DatabaseHelper tmpDBHelper = new DatabaseHelper(getActivity());


        //set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setTitle(R.string.popup_title).setView(linearLayout)
                // Add action buttons
                .setNegativeButton(R.string.popup_cancelButtonText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MTPopupDialogFragment.this.getDialog().cancel();
						tmpDBHelper.close();
                    }
                })
                .setNeutralButton(R.string.popup_deleteButtonText, new DialogInterface.OnClickListener() {
                    @Override
                    //NOT WORKING FOR NOW!!!!
                    //Have to find a way to refresh the listFragment so that the deleted item will disappear

                    public void onClick(DialogInterface dialogInterface, int i) {
                        tmpDBHelper.deleteLocation(String.valueOf(ListDialogTransactor.locationInfo.getTime()));
                        MTPopupDialogFragment.this.getDialog().dismiss();
						tmpDBHelper.close();
						MTListFragment.getInstance().refresh();
                        MTMapFragment.getInstance().refresh();
                        Toast.makeText(getActivity(), "Delete Location successful", Toast.LENGTH_SHORT).show();

                    }

                })
                .setPositiveButton(R.string.popup_addButtonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //read user input from editText
                        final EditText editText = (EditText)addDscrp.findViewById(R.id.editText2);
                        String newDescription = editText.getText().toString();
                        LocationInfo locationInfo;
                        if(!newDescription.isEmpty()){
                            locationInfo = new LocationInfo(ListDialogTransactor.locationInfo.getTime(),
                                    ListDialogTransactor.locationInfo.get_Latitude(),ListDialogTransactor.locationInfo.get_Longitude(),
                                    newDescription);
                            Toast.makeText(getActivity(), "Add descriptions successful", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            locationInfo = new LocationInfo(ListDialogTransactor.locationInfo.getTime(),
                                    ListDialogTransactor.locationInfo.get_Latitude(),ListDialogTransactor.locationInfo.get_Longitude(),
                                    ListDialogTransactor.description);
                        }

                        tmpDBHelper.updateLocation(locationInfo);
						tmpDBHelper.close();
                        MTMapFragment.getInstance().refresh();
                        MTPopupDialogFragment.this.getDialog().dismiss();

                    }
                });


        return builder.create();
    }


    @Override
    public void onDismiss (DialogInterface dialog){
        super.onDismiss(dialog);
        ListDialogTransactor.reset();
    }

    @Override
    public void onCancel (DialogInterface dialog){
        super.onCancel(dialog);
        ListDialogTransactor.reset();
    }



}
