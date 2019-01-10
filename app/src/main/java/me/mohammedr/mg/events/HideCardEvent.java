package me.mohammedr.mg.events;

/**
 * Hide card event from the board controller to hide the pair of cards
 */
public class HideCardEvent extends BaseEvent {
    private final int[] pairOfFlippedIds;

    public HideCardEvent(int[] pairOfFlippedIds) {
        super(EventType.HideCardEvent);
        this.pairOfFlippedIds = pairOfFlippedIds;
    }

    public int[] getPairOfFlippedIds() {
        return pairOfFlippedIds;
    }
}
