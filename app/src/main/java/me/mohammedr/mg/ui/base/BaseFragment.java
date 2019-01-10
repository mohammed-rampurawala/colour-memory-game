package me.mohammedr.mg.ui.base;

import dagger.android.support.DaggerFragment;

/**
 * Base Fragment to all the fragments in app.
 */
public abstract class BaseFragment extends DaggerFragment {

    public boolean onBackPressed() {
        return false;
    }
}
