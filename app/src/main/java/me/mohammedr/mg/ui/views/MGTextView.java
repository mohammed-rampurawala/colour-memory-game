package me.mohammedr.mg.ui.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class MGTextView extends TextView {
    public MGTextView(Context context) {
        super(context);
        init();
    }

    public MGTextView(Context context, @androidx.annotation.Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MGTextView(Context context, @androidx.annotation.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        setTypeface(Typeface.createFromAsset(getResources().getAssets(), "game_font.ttf"));
    }

}
