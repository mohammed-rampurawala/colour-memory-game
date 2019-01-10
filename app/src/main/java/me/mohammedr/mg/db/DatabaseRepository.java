package me.mohammedr.mg.db;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import me.mohammedr.mg.db.entity.ScoresEntity;
import me.mohammedr.mg.model.PlayerDetailsModel;
import me.mohammedr.mg.model.ScoresModel;

/**
 * Database Repository to access the game database
 */
public class DatabaseRepository implements IPersistenceRepository {

    private MemoryGameDatabase database;

    @Inject
    public DatabaseRepository(MemoryGameDatabase database) {
        this.database = database;
    }

    @Override
    public void insertPlayerDetails(PlayerDetailsModel playerDetails) {
        CompositeDisposable disposable = new CompositeDisposable();
        disposable.add(Single.just(playerDetails)
                .map(playerDetails1 -> {
                    ScoresEntity entity = new ScoresEntity();
                    entity.setName(playerDetails1.getName());
                    entity.setScore(Integer.parseInt(playerDetails1.getScore()));
                    return entity;
                })
                .subscribeOn(Schedulers.io())
                .map(highScoreEntity -> database.getScoresDao().insertHighScore(highScoreEntity))
                .subscribe(rowId -> disposable.dispose(), throwable -> {
                    throw new Exception("Unable to insert player details");
                }));
    }

    @Override
    public LiveData<Integer> getHighScore() {
        return database.getScoresDao().getHighScore();
    }

    @Override
    public LiveData<List<ScoresModel>> getScores() {
        return database.getScoresDao().getAllScores();
    }
}
