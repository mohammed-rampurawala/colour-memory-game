package me.mohammedr.mg.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Scores table containing the player details who has successfully completed game.
 */
@Entity(tableName = "scores")
public class ScoresEntity {

    /**
     * Primary key for the table
     */
    @PrimaryKey(autoGenerate = true)
    private int id;

    /**
     * Name of a player
     */
    @NonNull
    @ColumnInfo(name = "player_name")
    private String name = "";

    /**
     * Score of player
     */
    private int score;

    /**
     * TODO : To be added in future to display the time required to complete a game
     * Time required to complete a game (in milliseconds)
     */
    @Ignore
    private long time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
