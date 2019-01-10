package me.mohammedr.mg.events;

/**
 * Base events for the all the game events
 */
public abstract class BaseEvent {
    /**
     * Type of current event
     */
    private EventType eventType;

    BaseEvent(EventType eventType) {
        this.eventType = eventType;
    }

    /**
     * Get the current type of event
     *
     * @return EventType
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Constant containing type of events
     */
    public enum EventType {
        FlipCardDownEvent,
        HideCardEvent,
        GameWonEvent
    }
}
