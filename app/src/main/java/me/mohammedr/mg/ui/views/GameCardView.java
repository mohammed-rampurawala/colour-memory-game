package me.mohammedr.mg.ui.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.makeramen.roundedimageview.RoundedImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import me.mohammedr.mg.R;


/**
 * Single Card Tile
 */
public class GameCardView extends FrameLayout {

    private RoundedImageView mTopImage;
    private RoundedImageView mTileImage;
    private boolean mFlippedDown = true;

    public GameCardView(Context context) {
        this(context, null, -1);
    }

    public GameCardView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public GameCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * Initialize the card variables
     *
     * @param context of application
     */
    private void init(Context context) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mTileImage = new RoundedImageView(context);
        mTileImage.setLayoutParams(params);
        mTileImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mTileImage.setCornerRadius(30);
        addView(mTileImage);

        mTopImage = new RoundedImageView(context);
        mTopImage.setLayoutParams(params);
        mTopImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mTopImage.setCornerRadius(30);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mTopImage.setImageDrawable(getResources().getDrawable(R.drawable.card_bg, getResources().newTheme()));
        } else {
            mTopImage.setImageDrawable(getResources().getDrawable(R.drawable.card_bg));
        }
        addView(mTopImage);
    }


    /**
     * Set the card image
     *
     * @param bitmap card image
     */
    public void setCardBitmap(Bitmap bitmap) {
        mTileImage.setImageBitmap(bitmap);
    }

    /**
     * Flip the card up
     */
    public void flipUp() {
        mFlippedDown = false;
        flip();
    }

    /**
     * Flip card down
     */
    public void flipDown() {
        mFlippedDown = true;
        flip();
    }

    /**
     * Flip card with animation
     */
    private void flip() {
        FlipAnimation flipAnimation = new FlipAnimation(mTopImage, mTileImage);
        if (mTopImage.getVisibility() == View.GONE) {
            flipAnimation.reverse();
        }
        startAnimation(flipAnimation);
    }

    /**
     * is card flipped down
     *
     * @return True if down. False otherwise
     */
    public boolean isFlippedDown() {
        return mFlippedDown;
    }

    /**
     * Flip card animation
     */
    public class FlipAnimation extends Animation {
        private Camera camera;

        private View fromView;
        private View toView;

        private float centerX;
        private float centerY;

        private boolean forward = true;

        /**
         * Creates a 3D flip animation between two views.
         *
         * @param fromView First view in the transition.
         * @param toView   Second view in the transition.
         */
        public FlipAnimation(View fromView, View toView) {
            this.fromView = fromView;
            this.toView = toView;

            setDuration(700);
            setFillAfter(false);
            setInterpolator(new AccelerateDecelerateInterpolator());
        }

        public void reverse() {
            forward = false;
            View switchView = toView;
            toView = fromView;
            fromView = switchView;
        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            centerX = width / 2;
            centerY = height / 2;
            camera = new Camera();
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            // Angle around the y-axis of the rotation at the given time
            // calculated both in radians and degrees.
            final double radians = Math.PI * interpolatedTime;
            float degrees = (float) (180.0 * radians / Math.PI);

            // Once we reach the midpoint in the animation, we need to hide the
            // source view and show the destination view. We also need to change
            // the angle by 180 degrees so that the destination does not come in
            // flipped around
            if (interpolatedTime >= 0.5f) {
                degrees -= 180.f;
                fromView.setVisibility(View.GONE);
                toView.setVisibility(View.VISIBLE);
            }

            if (forward)
                degrees = -degrees; // determines direction of rotation when
            // flip begins

            final Matrix matrix = t.getMatrix();
            camera.save();
            camera.rotateX(degrees);
            camera.getMatrix(matrix);
            camera.restore();
            matrix.preTranslate(-centerX, -centerY);
            matrix.postTranslate(centerX, centerY);
        }
    }
}
