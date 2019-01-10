package me.mohammedr.mg.ui.start;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.core.app.ActivityOptionsCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.mohammedr.mg.R;
import me.mohammedr.mg.ui.base.BaseActivity;
import me.mohammedr.mg.ui.game.GameActivity;

/**
 * Activity will be shown in the start
 */
public class StartGameActivity extends BaseActivity {

    @BindView(R.id.splash_logo)
    ImageView splashLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.play_icon)
    public void onPlayClick(View v) {
        Intent intent = new Intent(getApplicationContext(), GameActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(StartGameActivity.this, splashLogo, getString(R.string.app_logo));
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

}
