package me.mohammedr.mg.model;

/**
 * Player details
 */
public class PlayerDetailsModel {
    /**
     * Name of the player
     */
    private String name;

    /**
     * Current score of the player
     */
    private int score;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setScore(String score) {
        this.score = Integer.valueOf(score);
    }

    public String getScore() {
        return String.valueOf(score);
    }
}
