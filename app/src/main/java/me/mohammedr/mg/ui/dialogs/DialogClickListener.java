package me.mohammedr.mg.ui.dialogs;

import me.mohammedr.mg.model.PlayerDetailsModel;

/**
 * Click listener for the dialog continue and restart button
 *
 * @param <T>
 */
public interface DialogClickListener<T> {

    /**
     * When user clicks continue button on dialog
     *
     * @param t type of object
     */
    void onContinueClicked(T t);

    /**
     * Listener when user clicks restart button on dialog
     *
     * @param t type of object
     */
    void onRestart(T t);
}
