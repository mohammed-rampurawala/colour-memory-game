package me.mohammedr.mg.events;

/**
 * Score update event. Whenever user makes a right or wrong move, this event is fired and score is
 * updated. If Game is finished the isGameFinished variable returns {@link Boolean true}
 */
public class ScoreUpdateEvent extends BaseEvent {
    private int score;
    private boolean isGameFinished;

    public ScoreUpdateEvent() {
        super(EventType.GameWonEvent);
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setGameFinished(boolean gameFinished) {
        isGameFinished = gameFinished;
    }

    public boolean isGameFinished() {
        return isGameFinished;
    }

    public int getScore() {
        return score;
    }
}
