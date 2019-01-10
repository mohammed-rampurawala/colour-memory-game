package me.mohammedr.mg.ui.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.LinearLayout;

import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.PublishSubject;
import me.mohammedr.mg.controller.GameConfig;
import me.mohammedr.mg.R;
import me.mohammedr.mg.events.FlipEvent;
import me.mohammedr.mg.utils.GlideApp;

/**
 * Custom board view of the flip card game
 */
public class BoardView extends LinearLayout implements View.OnClickListener {

    private LinearLayout.LayoutParams mRowLayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    private LinearLayout.LayoutParams mTileLayoutParams;
    private int mScreenWidth;
    private int mScreenHeight;
    private Map<Integer, GameCardView> mViewReference;
    private List<Integer> mFlippedUpCardIds = new ArrayList<Integer>();
    private boolean mLocked = false;
    private int mTilesHeight;
    private int mTilesWidth;
    private GameConfig mGameConfig;
    private PublishSubject<FlipEvent> mFlippedCardObservable;

    public BoardView(Context context) {
        this(context, null);
    }

    public BoardView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER);
        int margin = getResources().getDimensionPixelSize(R.dimen.margine_top);
        int padding = getResources().getDimensionPixelSize(R.dimen.board_padding);
        mScreenHeight = getResources().getDisplayMetrics().heightPixels - margin - padding * 2;
        mScreenWidth = getResources().getDisplayMetrics().widthPixels - padding * 2 - ((int) getResources().getDisplayMetrics().density * 20);
        mViewReference = new HashMap<>();
        setClipToPadding(false);
    }

    private void initObservables() {
        mFlippedCardObservable = PublishSubject.create();
    }

    public Observable<FlipEvent> getFlipEventObservable() {
        return mFlippedCardObservable;
    }

    /**
     * Set the Board view according to the game config
     *
     * @param gameConfig by which board view needs to be prepared
     */
    public void setBoard(GameConfig gameConfig) {
        initObservables();
        this.mGameConfig = gameConfig;

        int singleMargin = (int) (2 * getResources().getDisplayMetrics().density);
        int sumMargin = 0;
        for (int row = 0; row < gameConfig.getRowAndColPair().first; row++) {
            sumMargin += singleMargin * 2;
        }
        mTilesHeight = (mScreenHeight - sumMargin) / gameConfig.getRowAndColPair().second;
        mTilesWidth = (mScreenWidth - sumMargin) / gameConfig.getRowAndColPair().first;

        mTileLayoutParams = new LinearLayout.LayoutParams(mTilesWidth, mTilesHeight);
        mTileLayoutParams.setMargins(singleMargin, singleMargin, singleMargin, singleMargin);

        // build the ui
        buildBoard();
    }

    /**
     * Build the board
     */
    private void buildBoard() {

        for (int row = 0; row < mGameConfig.getRowAndColPair().second; row++) {
            // add row
            addBoardRow(row, mGameConfig);
        }

        setClipChildren(false);
    }

    /**
     * Add the board rows
     *
     * @param rowNum which need to be added
     * @param config configuration of the game. For more references: {@link GameConfig}
     */
    private void addBoardRow(int rowNum, GameConfig config) {

        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);

        for (int tile = 0; tile < config.getRowAndColPair().first; tile++) {
            addTile(rowNum * config.getRowAndColPair().first + tile, linearLayout);
        }

        // add to this view
        addView(linearLayout, mRowLayoutParams);
        linearLayout.setClipChildren(false);
    }

    /**
     * Add the {@link GameCardView} into the board view with the card random image.
     *
     * @param id     of the tile that needs to be matched when the cards are flipped.
     * @param parent in which {@link GameCardView} need to added.
     */
    private void addTile(final int id, ViewGroup parent) {
        GameCardView gameCardView = new GameCardView(getContext());
        gameCardView.setLayoutParams(mTileLayoutParams);
        gameCardView.setTag(id);
        parent.addView(gameCardView);
        parent.setClipChildren(false);
        mViewReference.put(id, gameCardView);

        GlideApp.with(getContext())
                .asBitmap()
                .load(mGameConfig.getDrawableId(id))
                .override(mTilesWidth, mTilesHeight)
                .into(new CustomViewTarget<GameCardView, Bitmap>(gameCardView) {
                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {

                    }

                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        this.getView().setCardBitmap(resource);
                    }

                    @Override
                    protected void onResourceCleared(@Nullable Drawable placeholder) {

                    }
                }).clearOnDetach();

        gameCardView.setOnClickListener(this);

        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(gameCardView, "scaleX", 0.8f, 1f);
        scaleXAnimator.setInterpolator(new BounceInterpolator());
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(gameCardView, "scaleY", 0.8f, 1f);
        scaleYAnimator.setInterpolator(new BounceInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
        animatorSet.setDuration(500);
        gameCardView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        animatorSet.start();
    }


    /**
     * Flip down all the cards that we're holding up.
     */
    public void flipDownAll() {
        for (Integer id : mFlippedUpCardIds) {
            GameCardView gameCardViewReference = mViewReference.get(id);
            if (gameCardViewReference != null) {
                gameCardViewReference.flipDown();
            }
        }
        unlockCards();
    }

    /**
     * Unlock more cards to get flipped
     */
    private void unlockCards() {
        mFlippedUpCardIds.clear();

        Observable.just(1)
                .delay(100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(no -> mLocked = false);
    }

    /**
     * Hide all the cards that have been hold up. This event occurs when the card in matched.
     *
     * @param arr ids of the cards that needs to hidden
     */
    public void hideCards(int... arr) {
        for (int id : arr) {
            animateHide(mViewReference.get(id));
        }
        unlockCards();
    }

    /**
     * Animate hiding of the card
     *
     * @param v view needs to be hidden
     */
    protected void animateHide(final GameCardView v) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "alpha", 0f);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                v.setLayerType(View.LAYER_TYPE_NONE, null);
                v.setVisibility(View.INVISIBLE);
            }
        });
        animator.setDuration(100);
        v.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        animator.start();
    }


    @Override
    public void onClick(View view) {
        GameCardView gameCardView = (GameCardView) view;
        if (!mLocked && gameCardView.isFlippedDown()) {
            gameCardView.flipUp();
            mFlippedUpCardIds.add((Integer) gameCardView.getTag());
            if (mFlippedUpCardIds.size() == 2) {
                mLocked = true;
            }
            mFlippedCardObservable.onNext(FlipEvent.getObject((Integer) gameCardView.getTag()));
        }
    }

    @Override
    public void onViewRemoved(View child) {
        super.onViewRemoved(child);
        mFlippedCardObservable.onComplete();
    }

    /**
     * Reset the board variables
     */
    public void reset() {
        removeAllViews();
        mLocked = false;
        mViewReference.clear();
        mFlippedUpCardIds.clear();
    }
}

