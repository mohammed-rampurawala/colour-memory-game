package me.mohammedr.mg.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import me.mohammedr.mg.R;
import me.mohammedr.mg.ui.views.MGTextView;

/**
 * Scores textview with color shader text
 */
public class ScoreTextView extends MGTextView {

    public ScoreTextView(Context context) {
        this(context, null, -1);

    }

    public ScoreTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);

    }

    public ScoreTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ScoreTextView, 0, 0);
        try {
            setShader(ta.getBoolean(R.styleable.ScoreTextView_isMultiColor, true));
        } finally {
            ta.recycle();
        }
        setTypeface(Typeface.createFromAsset(getResources().getAssets(), "obelix_pro_regular.ttf"));
    }

    /**
     * Set the color shader
     *
     * @param isShader True if text shader is needed. False, otherwise
     */
    private void setShader(boolean isShader) {
        if (isShader) {
            getPaint().setShader(new LinearGradient(0, 0, 0, getLineHeight(), new int[]{
                    Color.parseColor("#F97C3C"),
                    Color.parseColor("#FDB54E"),
                    Color.parseColor("#64B678"),
                    Color.parseColor("#478AEA"),
                    Color.parseColor("#8446CC"),
            }, null, Shader.TileMode.REPEAT));
        }
    }

}
