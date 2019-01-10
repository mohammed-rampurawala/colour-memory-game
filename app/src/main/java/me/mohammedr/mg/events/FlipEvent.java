package me.mohammedr.mg.events;

/**
 * Flip event from {@link me.mohammedr.mg.ui.views.BoardView} when user clicks on card.
 * This event contains the id of the card which is being flipped currently.
 */
public class FlipEvent {
    private int idFlipped;

    public FlipEvent(int id) {
        idFlipped = id;
    }

    public static FlipEvent getObject(int id) {
        return new FlipEvent(id);
    }

    public int getIdFlipped() {
        return idFlipped;
    }
}
