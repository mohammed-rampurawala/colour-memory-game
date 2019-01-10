package me.mohammedr.mg.ui.game;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import io.reactivex.disposables.CompositeDisposable;
import me.mohammedr.mg.controller.BoardController;

public class GameViewModelFactory implements ViewModelProvider.Factory {
    private final CompositeDisposable disposable;
    private final BoardController controller;

    public GameViewModelFactory(BoardController controller, CompositeDisposable disposable) {
        this.controller = controller;
        this.disposable = disposable;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(GameViewModel.class)) {
            return (T) new GameViewModel(controller, disposable);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
