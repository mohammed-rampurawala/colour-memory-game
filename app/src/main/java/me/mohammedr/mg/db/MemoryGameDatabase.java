package me.mohammedr.mg.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import me.mohammedr.mg.db.dao.ScoresDao;
import me.mohammedr.mg.db.entity.ScoresEntity;

/**
 * Room implementation of android database
 */
@Database(entities = {ScoresEntity.class}, version = 1)
public abstract class MemoryGameDatabase extends RoomDatabase {
    /**
     * Get the scores dao
     *
     * @return ScoresDao
     */
    abstract ScoresDao getScoresDao();
}
