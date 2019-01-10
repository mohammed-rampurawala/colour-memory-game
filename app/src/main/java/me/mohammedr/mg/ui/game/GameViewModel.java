package me.mohammedr.mg.ui.game;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import me.mohammedr.mg.controller.BoardController;
import me.mohammedr.mg.controller.GameConfig;
import me.mohammedr.mg.controller.GameTypeEnum;
import me.mohammedr.mg.events.BaseEvent;
import me.mohammedr.mg.events.FlipEvent;

/**
 * Game View model providing data needed which game is being played
 */
public class GameViewModel extends ViewModel {

    /**
     * Controlling and providing the needed computation to perform game operations
     */
    private BoardController mBoardController;

    /**
     * Disposable for the Observables
     */
    private CompositeDisposable mCompositeDisposable;

    /**
     * {@link GameConfig} needed to draw the board when initial computation is made by the board
     * controller
     */
    private MutableLiveData<GameConfig> mGameConfigLiveData = new MutableLiveData<>();

    /**
     * Game events from the controller
     */
    private MutableLiveData<BaseEvent> mGameEvents = new MutableLiveData<>();

    public GameViewModel(BoardController controller, CompositeDisposable disposable) {
        this.mBoardController = controller;
        this.mCompositeDisposable = disposable;
        controller.createListeners();
    }

    /**
     * Start the game
     *
     * @param tileResIds tile ids to be hidden in cards
     */
    public void startGame(List<Integer> tileResIds) {
        reset();
        mCompositeDisposable.add(mBoardController.getStartGameEventObservable()
                .delay(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(config -> {
                    mGameConfigLiveData.setValue(config);
                    initBoardListeners();
                }, throwable -> {
                }));
        mBoardController.startGame(GameTypeEnum.DEFAULT_TILES, tileResIds);
    }

    /**
     * Initialize the board listeners
     */
    private void initBoardListeners() {
        mCompositeDisposable.add(mBoardController.getFlipDownEventObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(flipCardDownEvent -> mGameEvents.setValue(flipCardDownEvent)));

        mCompositeDisposable.add(mBoardController.getHideEventObservable().observeOn(AndroidSchedulers.mainThread())
                .subscribe(hideCardEvent -> mGameEvents.setValue(hideCardEvent)));

        mCompositeDisposable.add(mBoardController.getScoreUpdateEvent().observeOn(AndroidSchedulers.mainThread())
                .subscribe(scoreUpdateEvent -> mGameEvents.setValue(scoreUpdateEvent)));
    }

    public LiveData<GameConfig> getGameConfigLiveData() {
        return mGameConfigLiveData;
    }

    public MutableLiveData<BaseEvent> getGameEvents() {
        return mGameEvents;
    }

    /**
     * Reset all the variables
     */
    public void reset() {
        mCompositeDisposable.dispose();
        mCompositeDisposable = new CompositeDisposable();
        mBoardController.clearData();
    }

    /**
     * When a card is flipped
     *
     * @param flipEvent event containing the id
     */
    public void tileFlipped(FlipEvent flipEvent) {
        mBoardController.tileFlipped(flipEvent);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.dispose();
        mBoardController.clearData();
    }
}
