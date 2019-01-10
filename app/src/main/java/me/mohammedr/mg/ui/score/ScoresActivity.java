package me.mohammedr.mg.ui.score;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import me.mohammedr.mg.R;
import me.mohammedr.mg.model.ScoresModel;
import me.mohammedr.mg.ui.base.BaseActivity;
import me.mohammedr.mg.ui.view_model.DBViewModel;
import me.mohammedr.mg.ui.view_model.DBViewModelFactory;

/**
 * Player scores activity
 */
public class ScoresActivity extends BaseActivity {

    /**
     * Binder of views
     */
    private Unbinder bind;

    /**
     * Scores recyclerview
     */
    @BindView(R.id.scores_recycler_view)
    RecyclerView mScoresRecyclerView;

    /**
     * Screen title textview
     */
    @BindView(R.id.scores_title)
    TextView mTitleTV;

    /**
     * No data textview
     */
    @BindView(R.id.empty_view)
    TextView mNoDataTV;

    /**
     * Scores adapter for mScoresRecyclerView
     */
    private ScoresAdapter mScoresAdapter;

    /**
     * Disposable for RX observables
     */
    @Inject
    CompositeDisposable mDisposable;

    /**
     * View model factory for {@link DBViewModel}
     */
    @Inject
    DBViewModelFactory mDBViewModelFactory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_score_activity);
        bind = ButterKnife.bind(this);
        mTitleTV.setText(getString(R.string.leaderboard_title));
        mNoDataTV.setText(getString(R.string.no_data_available));
        DBViewModel dbViewModel = ViewModelProviders.of(this, mDBViewModelFactory).get(DBViewModel.class);
        mDisposable = new CompositeDisposable();
        mDisposable.add(dbViewModel.getAllScores(this).subscribe(new Consumer<List<ScoresModel>>() {
            @Override
            public void accept(List<ScoresModel> scoresModels) throws Exception {
                updateScoresList(scoresModels);
            }
        }));
    }

    /**
     * Updates the score list in the recycler view
     *
     * @param scoresList list of high scores
     */
    private void updateScoresList(List<ScoresModel> scoresList) {
        hideEmptyView();
        if (mScoresAdapter == null) {
            mScoresAdapter = new ScoresAdapter(LayoutInflater.from(this));
            LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            mScoresRecyclerView.setLayoutManager(manager);
            mScoresRecyclerView.setAdapter(mScoresAdapter);
        }
        mScoresAdapter.setScoresList(scoresList);
    }

    /**
     * Hide empty view
     */
    private void hideEmptyView() {
        mNoDataTV.setVisibility(View.INVISIBLE);
        mScoresRecyclerView.setVisibility(View.VISIBLE);
        mTitleTV.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        mDisposable.dispose();
    }
}
