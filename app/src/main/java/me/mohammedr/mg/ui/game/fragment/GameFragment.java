package me.mohammedr.mg.ui.game.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import me.mohammedr.mg.R;
import me.mohammedr.mg.events.BaseEvent;
import me.mohammedr.mg.events.HideCardEvent;
import me.mohammedr.mg.events.ScoreUpdateEvent;
import me.mohammedr.mg.ui.base.BaseFragment;
import me.mohammedr.mg.ui.dialogs.DialogClickListener;
import me.mohammedr.mg.ui.dialogs.ExitGameDialog;
import me.mohammedr.mg.ui.dialogs.GameWonDialog;
import me.mohammedr.mg.ui.game.GameViewModel;
import me.mohammedr.mg.ui.game.GameViewModelFactory;
import me.mohammedr.mg.model.PlayerDetailsModel;
import me.mohammedr.mg.ui.score.ScoresActivity;
import me.mohammedr.mg.ui.view_model.DBViewModel;
import me.mohammedr.mg.ui.view_model.DBViewModelFactory;
import me.mohammedr.mg.ui.views.BoardView;
import me.mohammedr.mg.utils.Constants;
import me.mohammedr.mg.utils.Utils;

public class GameFragment extends BaseFragment {

    /**
     * Factory object of {@link GameViewModel}
     */
    @Inject
    GameViewModelFactory mGameViewModelFactory;

    /**
     * Factory object of {@link DBViewModel}
     */
    @Inject
    DBViewModelFactory mDBViewModelFactory;

    /**
     * Disposable for RxObservables
     */
    private CompositeDisposable mDisposable;

    /**
     * Game cards to be loaded into the {@link BoardView}
     */
    @BindView(R.id.board_view)
    BoardView mBoardView;

    /**
     * Current score of user playing game
     */
    @BindView(R.id.score_tv)
    TextView mScoreTV;

    /**
     * Logo view shown on top left side of screen
     */
    @BindView(R.id.logo_iv)
    ImageView mLogoView;

    /**
     * ViewModel responsible to control the game.
     */
    private GameViewModel mGameViewModel;

    /**
     * ViewModel responsible for DB operations
     */
    private DBViewModel mDBViewModel;

    /**
     * Unbinder for the views
     */
    private Unbinder mUnbinder;

    /**
     * State of the player if made any moves.
     * True, if player made any moves. False otherwise
     */
    private boolean mPlayerMadeMoves = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_fragment, container, false);
        mDisposable = new CompositeDisposable();
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGameViewModel = ViewModelProviders.of(this, mGameViewModelFactory).get(GameViewModel.class);
        mDBViewModel = ViewModelProviders.of(this, mDBViewModelFactory).get(DBViewModel.class);
        mGameViewModel.getGameConfigLiveData().observe(this, config -> {
            mBoardView.setBoard(config);
            initBoardListeners();
        });
        mGameViewModel.getGameEvents().observe(this, this::handleEvent);
        mGameViewModel.startGame(Utils.getResourceIds(getResources().obtainTypedArray(R.array.colour_ids)));
    }

    /**
     * Handle the events fired by the game view model
     *
     * @param event type of event
     */
    private void handleEvent(BaseEvent event) {
        switch (event.getEventType()) {
            case FlipCardDownEvent:
                mBoardView.flipDownAll();
                break;
            case GameWonEvent:
                updateScore(event);
                break;
            case HideCardEvent:
                mBoardView.hideCards(((HideCardEvent) event).getPairOfFlippedIds());
                break;
            default:
                break;
        }
    }

    /**
     * Update score on screen, show game pop up if all the cards are hidden
     *
     * @param event score event
     */
    private void updateScore(BaseEvent event) {
        ScoreUpdateEvent scoreEvent = (ScoreUpdateEvent) event;
        mScoreTV.setText(String.valueOf(scoreEvent.getScore()));
        if (scoreEvent.isGameFinished()) {
            showGamePopup(scoreEvent);
        }
    }

    /**
     * Show game won popup when user successfully matched all the cards
     *
     * @param scoreEvent score event
     */
    private void showGamePopup(ScoreUpdateEvent scoreEvent) {
        GameWonDialog dialog = new GameWonDialog();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.SCORE_KEY, String.valueOf(scoreEvent.getScore()));
        dialog.setArguments(bundle);
        dialog.show(getChildFragmentManager(), GameWonDialog.class.getSimpleName());
        dialog.setOnDialogClickListener(new DialogClickListener<PlayerDetailsModel>() {
            @Override
            public void onContinueClicked(PlayerDetailsModel playerDetails) {
                mDBViewModel.insert(playerDetails);
                dialog.dismiss();
                startHighScoreActivity();
                getActivity().finish();
            }

            @Override
            public void onRestart(PlayerDetailsModel playerDetails) {
                mPlayerMadeMoves = false;
                mDBViewModel.insert(playerDetails);
                mBoardView.reset();
                mGameViewModel.startGame(Utils.getResourceIds(getResources().obtainTypedArray(R.array.colour_ids)));
                dialog.dismiss();
            }
        });
    }

    /**
     * Start the scores activity
     */
    private void startHighScoreActivity() {
        startActivity(new Intent(getContext(), ScoresActivity.class));
    }

    /**
     * Initialize listeners the board listener like Flip
     */
    private void initBoardListeners() {
        mDisposable.add(mBoardView.getFlipEventObservable().subscribe(flipEvent -> {
            mGameViewModel.tileFlipped(flipEvent);
            mPlayerMadeMoves = true;
        }, throwable -> {

        }));
    }


    @OnClick(R.id.highscore_container)
    void onHighScoreContainerClicked() {
        startHighScoreActivity();
    }

    @Override
    public boolean onBackPressed() {
        //If player made any moves then ask for user confirmation
        if (mPlayerMadeMoves) {
            ExitGameDialog dialog = new ExitGameDialog();
            dialog.show(getChildFragmentManager(), ExitGameDialog.class.getSimpleName());
            return true;
        }
        return super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        mDisposable.dispose();
    }
}
