package me.mohammedr.mg.ui.view_model;

import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import me.mohammedr.mg.db.IPersistenceRepository;
import me.mohammedr.mg.model.PlayerDetailsModel;
import me.mohammedr.mg.model.ScoresModel;

/**
 * View model for the DB
 */
public class DBViewModel extends ViewModel {

    private IPersistenceRepository repository;

    DBViewModel(IPersistenceRepository repository) {
        this.repository = repository;
    }

    /**
     * Insert the player details into the database
     *
     * @param playerDetails details of player
     */
    public void insert(PlayerDetailsModel playerDetails) {
        repository.insertPlayerDetails(playerDetails);
    }

    /**
     * Get all the scores from the database
     *
     * @param owner owner of view lifecycle
     * @return RX Observable of List of {@link ScoresModel}
     */
    public Observable<List<ScoresModel>> getAllScores(LifecycleOwner owner) {
        return Flowable.fromPublisher(LiveDataReactiveStreams.toPublisher(owner, repository.getScores()))
                .toObservable()
                .filter(scoresModels -> scoresModels.size() > 0);
    }
}
