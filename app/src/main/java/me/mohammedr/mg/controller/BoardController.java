package me.mohammedr.mg.controller;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import me.mohammedr.mg.events.FlipCardDownEvent;
import me.mohammedr.mg.events.FlipEvent;
import me.mohammedr.mg.events.HideCardEvent;
import me.mohammedr.mg.events.ScoreUpdateEvent;

/**
 * Board controller is responsible for carrying out and board computation and checking of legitimacy of
 * operation if a move is made by a user.
 */
public class BoardController {
    private static final String TAG = BoardController.class.getSimpleName();

    private static final BoardController ourInstance = new BoardController();

    /**
     * Pair of ids currently is flipping
     */
    private int[] mPairOfFlippedIds;

    /**
     * Last index of paired flip id
     */
    private int lastIndexOfPairIdFlipped = 0;

    /**
     * Total no of tiles to be flipped
     */
    private int mTotalNoOfTilesToFlip = 0;

    /**
     * Current mScore of the game
     */
    private int mScore = 0;

    /**
     * Current game config
     */
    private GameConfig gameConfig;

    //Start: Events fired from the board
    private PublishSubject<GameConfig> startGameObsEvent;
    private PublishSubject<FlipCardDownEvent> flipCardDownEvent;
    private PublishSubject<HideCardEvent> hideCardEvent;
    private PublishSubject<ScoreUpdateEvent> scoreUpdateEvent;
    //End: Events fired from the board

    public static BoardController getInstance() {
        return ourInstance;
    }

    private BoardController() {
    }

    /**
     * Start the game with game type
     *
     * @param gameType    type of game (no of tiles and all)
     * @param drawableIds image ids to be mapped with card id
     */
    public void startGame(GameTypeEnum gameType, List<Integer> drawableIds) {
        resetFlippedIdArray(gameType.getMaxNoOfFlips());
        gameConfig = new GameConfig(gameType.getTiles(), gameType.getMaxNoOfFlips());

        mTotalNoOfTilesToFlip = gameType.getTiles();

        prepareGameData(drawableIds);
        startGameObsEvent.onNext(gameConfig);
    }

    /**
     * Initialize all the event publishers
     */
    private void initEventPublishers() {
        startGameObsEvent = PublishSubject.create();
        flipCardDownEvent = PublishSubject.create();
        hideCardEvent = PublishSubject.create();
        scoreUpdateEvent = PublishSubject.create();
    }

    /**
     * Prepare the board configurations according to the game type
     *
     * @param drawableIds ids to be mapped with card id
     */
    private void prepareGameData(List<Integer> drawableIds) {
        List<Integer> noOfTilesToShuffle = new ArrayList<>();
        for (int i = 0; i < mTotalNoOfTilesToFlip; i++) {
            noOfTilesToShuffle.add(i);
        }

        //Shuffle ids to not place items next to each other
        Collections.shuffle(noOfTilesToShuffle);

        Collections.shuffle(drawableIds);

        int j = 0;
        for (int i = 0; i < mTotalNoOfTilesToFlip; i++) {
            if (i + 1 < mTotalNoOfTilesToFlip) {
                // {4,"colour1"}, {2,"colour1"}, ...
                gameConfig.putInPair(noOfTilesToShuffle.get(i), noOfTilesToShuffle.get(i + 1));
                gameConfig.putInPair(noOfTilesToShuffle.get(i + 1), noOfTilesToShuffle.get(i));

                gameConfig.putInTileImage(noOfTilesToShuffle.get(i), drawableIds.get(j));
                gameConfig.putInTileImage(noOfTilesToShuffle.get(i + 1), drawableIds.get(j));
                i++;
                j++;
            }
        }

    }

    /**
     * Reset the flipped id array
     *
     * @param maxNoOfFlippedIds no of ids to be flipped
     */
    private void resetFlippedIdArray(int maxNoOfFlippedIds) {
        mPairOfFlippedIds = new int[maxNoOfFlippedIds];
        for (int i = 0; i < maxNoOfFlippedIds; i++) {
            mPairOfFlippedIds[i] = -1;
        }
        lastIndexOfPairIdFlipped = 0;
    }


    public Observable<GameConfig> getStartGameEventObservable() {
        return startGameObsEvent;
    }

    public Observable<HideCardEvent> getHideEventObservable() {
        return hideCardEvent.delay(1, TimeUnit.SECONDS);
    }

    public Observable<FlipCardDownEvent> getFlipDownEventObservable() {
        return flipCardDownEvent.delay(1, TimeUnit.SECONDS);
    }

    public Observable<ScoreUpdateEvent> getScoreUpdateEvent() {
        return scoreUpdateEvent.delay(1200, TimeUnit.MILLISECONDS);
    }

    /**
     * Create the listeners
     */
    public void createListeners() {
        initEventPublishers();
    }

    /**
     * When the tile is flipped. This method checks if user made the correct move or not.
     * Then fires events accordingly
     *
     * @param flipEvent card which is flipped
     */
    public void tileFlipped(FlipEvent flipEvent) {
        if (lastIndexOfPairIdFlipped < mPairOfFlippedIds.length - 1) {
            mPairOfFlippedIds[lastIndexOfPairIdFlipped] = flipEvent.getIdFlipped();
            lastIndexOfPairIdFlipped++;
            Log.d(TAG, "ID Added::" + flipEvent.getIdFlipped() + " Flip Size::" + lastIndexOfPairIdFlipped);
        } else {
            mPairOfFlippedIds[lastIndexOfPairIdFlipped] = flipEvent.getIdFlipped();
            lastIndexOfPairIdFlipped++;

            boolean matched = gameConfig.isMatched(mPairOfFlippedIds);
            Log.d(TAG, "Is ID matched::" + matched);
            if (matched) {
                hideCardEvent.onNext(new HideCardEvent(mPairOfFlippedIds));
                mTotalNoOfTilesToFlip -= gameConfig.getMaxNoOfCardFlips();
                mScore += 2;
            } else {
                mScore -= 1;
                flipCardDownEvent.onNext(new FlipCardDownEvent());
            }
            ScoreUpdateEvent event = new ScoreUpdateEvent();
            event.setGameFinished(mTotalNoOfTilesToFlip == 0);
            event.setScore(mScore);
            scoreUpdateEvent.onNext(event);
            resetFlippedIdArray(gameConfig.getMaxNoOfCardFlips());
        }
    }

    /**
     * Clear all the board data
     */
    public void clearData() {
        mPairOfFlippedIds = null;
        lastIndexOfPairIdFlipped = 0;
        mTotalNoOfTilesToFlip = 0;
        mScore = 0;
    }
}
