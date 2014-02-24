package com.cs48.myTrack;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

/**
 * Created by Administrator on 14-2-23.
 */
public class MTPopupDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setTitle(R.string.popup_title).setView(inflater.inflate(R.layout.popup, null))
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
                        Toast.makeText(getActivity(), "Deleted description successful", Toast.LENGTH_SHORT).show();
                    }

                })
                .setPositiveButton(R.string.popup_addButtonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        MTPopupDialogFragment.this.getDialog().cancel();
                        Toast.makeText(getActivity(), "Added description successful", Toast.LENGTH_SHORT).show();
                    }
                });


        return builder.create();
    }



}
