package com.cs48.myTrack;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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
        View addDscrp = inflater.inflate(R.layout.popup, null);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llp.setMargins(50, 10, 0, 0); // llp.setMargins(left, top, right, bottom);
        addDscrp.setBackgroundColor(Color.parseColor("#FFF5F5F5"));
        addDscrp.setLayoutParams(llp);

        //create two description textViews(title and content) that display current description
        LinearLayout.LayoutParams llp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llp1.setMargins(40, 13, 40, 0); // llp.setMargins(left, top, right, bottom);

        TextView currentDscrpTitle = new TextView(getActivity());
        String tmpDscrpTitle = "Current Description: ";
        currentDscrpTitle.setText(tmpDscrpTitle);
        currentDscrpTitle.setTextColor(Color.parseColor("#FF20B2AA"));//LightSeaGreen
        currentDscrpTitle.setLayoutParams(llp1);
        currentDscrpTitle.setTextSize(20);

        TextView currentDscrpContent = new TextView(getActivity());
        String tmpDscrpContent = "  "+ "\""+(DescriptionText.description)+"\"";
        DescriptionText.reset();
        currentDscrpContent.setText(tmpDscrpContent);
        currentDscrpContent.setTextColor(Color.parseColor("#FF696969"));//DimGray
        currentDscrpContent.setLayoutParams(llp1);
        currentDscrpContent.setHorizontallyScrolling(false);
        currentDscrpTitle.setTextSize(19);


        LinearLayout.LayoutParams llp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llp2.setMargins(0, 0, 0, 20); // llp.setMargins(left, top, right, bottom);
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(llp2);
        linearLayout.addView(currentDscrpTitle);
        linearLayout.addView(currentDscrpContent);
        linearLayout.addView(addDscrp);



        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setTitle(R.string.popup_title).setView(linearLayout)
                // Add action buttons
                .setNegativeButton(R.string.popup_cancelButtonText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MTPopupDialogFragment.this.getDialog().cancel();
                    }
                })
                .setNeutralButton(R.string.popup_deleteButtonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MTPopupDialogFragment.this.getDialog().cancel();
                        Toast.makeText(getActivity(), "Delete Location successful", Toast.LENGTH_SHORT).show();
                    }

                })
                .setPositiveButton(R.string.popup_addButtonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        MTPopupDialogFragment.this.getDialog().cancel();
                        Toast.makeText(getActivity(), "Add description successful", Toast.LENGTH_SHORT).show();
                    }
                });


        return builder.create();
    }



}
