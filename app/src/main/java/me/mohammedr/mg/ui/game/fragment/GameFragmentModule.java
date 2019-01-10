package me.mohammedr.mg.ui.game.fragment;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import me.mohammedr.mg.controller.BoardController;
import me.mohammedr.mg.ui.game.GameViewModelFactory;

@Module
public class GameFragmentModule {

    @Provides
    BoardController provideGameBoardController() {
        return BoardController.getInstance();
    }

    @Provides
    GameViewModelFactory provideGameModelFactory(BoardController controller, CompositeDisposable disposable) {
        return new GameViewModelFactory(controller, disposable);
    }
}
