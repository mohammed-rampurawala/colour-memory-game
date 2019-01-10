package me.mohammedr.mg.ui.score;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.mohammedr.mg.R;
import me.mohammedr.mg.model.ScoresModel;
import me.mohammedr.mg.utils.GlideApp;
import me.mohammedr.mg.utils.Utils;

/**
 * Scores adapter
 */
public class ScoresAdapter extends RecyclerView.Adapter<ScoresAdapter.ScoreViewHolder> {

    /**
     * Layout inflater
     */
    private final LayoutInflater mInflater;

    /**
     * List of scores
     */
    private List<ScoresModel> mList;

    public ScoresAdapter(LayoutInflater inflater) {
        mInflater = inflater;
    }

    /**
     * Sets the score list and notifies the recyclerview adapter that data has been changed
     *
     * @param scoresList
     */
    public void setScoresList(List<ScoresModel> scoresList) {
        mList = scoresList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ScoreViewHolder(mInflater.inflate(R.layout.scores_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    /**
     * View holder to bind the scores data
     */
    class ScoreViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.player_name_textview)
        TextView playerNameTextview;

        @BindView(R.id.star_image)
        ImageView imageView;

        @BindView(R.id.score_textview)
        TextView scoreTextview;

        @BindView(R.id.avatar)
        ImageView avatar;

        @BindView(R.id.rank_textview)
        TextView rankTextview;

        ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        /**
         * Bind the model data into the scores views
         *
         * @param scoresModel scores model
         */
        void bind(ScoresModel scoresModel) {
            playerNameTextview.setText(String.valueOf(scoresModel.getName()));
            imageView.setVisibility(scoresModel.isHighScore() ? View.VISIBLE : View.INVISIBLE);
            scoreTextview.setText(scoresModel.getScore());
            GlideApp.with(avatar)
                    .load(Utils.getRandomAvatar())
                    .into(avatar);
            rankTextview.setText(String.valueOf(scoresModel.getRank()));
        }
    }

}
