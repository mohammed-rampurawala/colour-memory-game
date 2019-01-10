package me.mohammedr.mg.model;

/**
 * Model object for containing the score of player
 */
public class ScoresModel {
    /**
     * Name of a player
     */
    private String name;

    /**
     * Score of a player
     */
    private String score;

    /**
     * Current rank of a player
     */
    private int rank;

    public String getName() {
        return name;
    }

    public String getScore() {
        return score;
    }

    public int getRank() {
        return rank;
    }

    public boolean isHighScore() {
        return rank == 1;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
