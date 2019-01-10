package me.mohammedr.mg.ui.game;

import android.os.Bundle;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.DaggerAppCompatActivity;
import dagger.android.support.HasSupportFragmentInjector;
import me.mohammedr.mg.R;
import me.mohammedr.mg.ui.base.BaseActivity;
import me.mohammedr.mg.ui.base.BaseFragment;
import me.mohammedr.mg.ui.dialogs.ExitGameDialog;

/**
 * Board game activity
 */
public class GameActivity extends DaggerAppCompatActivity implements ExitGameDialog.ExitGameDialogListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentByTag(getString(R.string.game_framgent_tag)) != null) {
            BaseFragment fragment = (BaseFragment) getSupportFragmentManager()
                    .findFragmentByTag(getString(R.string.game_framgent_tag));
            if (fragment != null && fragment.onBackPressed()) {
                return;
            }
        }
        super.onBackPressed();
        finish();
    }

    /**
     * When user clicks on OK button on {@link ExitGameDialog}
     */
    @Override
    public void onDialogPositiveClick() {
        finish();
    }

    /**
     * When user clicks on cancel button on {@link ExitGameDialog}
     */
    @Override
    public void onDialogNegativeClick() {

    }
}
