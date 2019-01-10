package me.mohammedr.mg.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import me.mohammedr.mg.R;

/**
 * Exit Game Dialog
 */
public class ExitGameDialog extends DialogFragment {

    private ExitGameDialogListener mListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.in_middle_of_game)
                .setMessage(R.string.you_want_to_exit)
                .setPositiveButton(android.R.string.ok, (dialog, id) -> {
                    if (mListener != null) {
                        mListener.onDialogPositiveClick();
                    }
                })
                .setNegativeButton(android.R.string.cancel, (dialog, id) -> {
                    dialog.dismiss();
                    if (mListener != null) {
                        mListener.onDialogNegativeClick();
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (ExitGameDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    public interface ExitGameDialogListener {
        void onDialogPositiveClick();

        void onDialogNegativeClick();
    }

}
