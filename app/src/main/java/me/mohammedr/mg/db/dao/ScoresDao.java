package me.mohammedr.mg.db.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import me.mohammedr.mg.db.entity.ScoresEntity;
import me.mohammedr.mg.model.ScoresModel;

/**
 * DAO to access the score table {@link ScoresEntity}.
 */
@Dao
public interface ScoresDao {

    /**
     * Get all the scores. This query will also calculate the rank of person.
     *
     * @return list of {@link ScoresModel}
     */
    @Query("SELECT prev_score.player_name as name, prev_score.score as score, COUNT (distinct current_score.score) as rank " +
            "FROM scores prev_score, scores current_score WHERE prev_score.score < current_score.score OR " +
            "(prev_score.score=current_score.score AND prev_score.player_name = current_score.player_name) " +
            "GROUP BY prev_score.player_name, prev_score.score ORDER BY prev_score.score DESC")
    LiveData<List<ScoresModel>> getAllScores();

    /**
     * Insert the score entry into the scores table
     *
     * @param scoresEntity to be inserted in table
     * @return row id where value is inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertHighScore(ScoresEntity scoresEntity);

    /**
     * Get the high score of a game
     *
     * @return LiveData of highscore in integer
     */
    @Query("SELECT score from scores ORDER BY score DESC LIMIT 1")
    LiveData<Integer> getHighScore();
}
