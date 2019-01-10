package me.mohammedr.mg.ui.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;
import me.mohammedr.mg.R;
import me.mohammedr.mg.utils.Constants;
import me.mohammedr.mg.utils.Utils;
import me.mohammedr.mg.model.PlayerDetailsModel;

/**
 * Game Won Dialog
 */
public class GameWonDialog extends DialogFragment {

    @BindView(R.id.current_score_textview)
    TextView mCurrentScoreTV;

    @BindView(R.id.message_textview)
    TextView mTitleMessageTV;

    @BindView(R.id.enter_name_et)
    EditText mPlayerNameET;

    /**
     * Name of a player to be stored when user starts typing
     */
    private String mPlayerName;

    /**
     * Binder objects of a view
     */
    private Unbinder mUnbinder;

    /**
     * Dialog button click listener
     */
    private DialogClickListener<PlayerDetailsModel> mDialogClickListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.game_won_dialog, null);
        builder.setView(view);
        mUnbinder = ButterKnife.bind(this, view);
        String score = getArguments().getString(Constants.SCORE_KEY);
        mCurrentScoreTV.setText(score);
        mTitleMessageTV.setText(getString(Utils.decideMessage(score)));
        setCancelable(false);
        return builder.create();
    }

    @OnTextChanged(R.id.enter_name_et)
    void onNameTextChanged(CharSequence s, int start, int before, int count) {
        mPlayerName = s.toString().trim();
        if (count > 0) {
            mPlayerNameET.setError(null);
        }
    }

    @OnClick(R.id.continue_button)
    void onContinue(View v) {
        if (TextUtils.isEmpty(mPlayerName)) {
            mPlayerNameET.setError(getString(R.string.enter_name_hint));
            return;
        }

        if (mDialogClickListener != null) {
            mDialogClickListener.onContinueClicked(preparePlayerDetails());
        }
    }

    @OnClick(R.id.restart_button)
    void onRestart() {
        if (TextUtils.isEmpty(mPlayerName)) {
            mPlayerNameET.setError(getString(R.string.enter_name_hint));
            return;
        }
        dismiss();
        if (mDialogClickListener != null) {
            mDialogClickListener.onRestart(preparePlayerDetails());
        }
    }

    /**
     * Prepare the player details to be used like name and score
     *
     * @return PlayerDetailsModel
     */
    private PlayerDetailsModel preparePlayerDetails() {
        Bundle arguments = getArguments();
        if (arguments == null || !arguments.containsKey(Constants.SCORE_KEY)) {
            throw new IllegalArgumentException("Unable to get score");
        }
        PlayerDetailsModel details = new PlayerDetailsModel();
        details.setName(mPlayerName);
        details.setScore(arguments.getString(Constants.SCORE_KEY));
        return details;
    }

    public void setOnDialogClickListener(DialogClickListener<PlayerDetailsModel> dialogClickListener) {
        this.mDialogClickListener = dialogClickListener;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
