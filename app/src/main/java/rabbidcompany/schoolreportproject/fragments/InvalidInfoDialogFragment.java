package rabbidcompany.schoolreportproject.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

import rabbidcompany.schoolreportproject.R;

/**
 * Created by noneemotion on 8/8/2559.
 */
public class InvalidInfoDialogFragment extends DialogFragment {
    public InvalidInfoDialogFragment() {
        super();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.fragment_invalid_info_dialog, null));
        builder.setNegativeButton("Try again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        //AlertDialog dialog01 = builder.create();
        //Button button01 = dialog01.getButton(DialogInterface.BUTTON_NEGATIVE);
        //button01.setTextColor(Color.YELLOW);

        //return super.onCreateDialog(savedInstanceState);
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize(18);
    }
}
