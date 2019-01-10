package me.mohammedr.mg.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import me.mohammedr.mg.model.PlayerDetailsModel;
import me.mohammedr.mg.model.ScoresModel;

/**
 * Interface to be extended to provide persistence storage to the app.
 */
public interface IPersistenceRepository {
    /**
     * Insert player details into the persistence storage
     *
     * @param playerDetails to be saved
     */
    void insertPlayerDetails(PlayerDetailsModel playerDetails);

    /**
     * Get the highscore
     *
     * @return livedata of highscore
     */
    LiveData<Integer> getHighScore();

    /**
     * Get the current scores list from the persistence storage.
     *
     * @return list of scores
     */
    LiveData<List<ScoresModel>> getScores();
}

