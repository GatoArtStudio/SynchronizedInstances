package art.gatoartstudio.synchronizedinstances.domains.player.domain.event;

import art.gatoartstudio.synchronizedinstances.domains.player.application.ports.in.DomainEventSubscriber;

public interface DomainEventBus {
    /**
     * Publish an event to all its subscribers.
     * @param event the event to publish
     */
    void publish(DomainEvent event);

    /**
     * Subscribe to an event.
     * @param subscriber the subscriber to add
     * @param <T> the type of event to subscribe to
     */
    <T extends DomainEvent> void subscribe(DomainEventSubscriber<T> subscriber);

    /**
     * Unsubscribe from an event.
     * @param subscriber the subscriber to remove
     * @param <T> the type of event to unsubscribe from
     */
    <T extends DomainEvent> void unsubscribe(DomainEventSubscriber<T> subscriber);
}
