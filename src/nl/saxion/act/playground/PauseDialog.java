package nl.saxion.act.playground;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class PauseDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_pause_msg)
               .setPositiveButton(R.string.dialog_pause_positive, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
            
                   }
               })
               .setNegativeButton(R.string.dialog_pause_negative, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {

                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}


