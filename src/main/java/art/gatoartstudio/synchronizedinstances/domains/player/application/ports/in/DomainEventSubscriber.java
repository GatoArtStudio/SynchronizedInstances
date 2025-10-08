package art.gatoartstudio.synchronizedinstances.domains.player.application.ports.in;

import art.gatoartstudio.synchronizedinstances.domains.player.domain.event.DomainEvent;

public interface DomainEventSubscriber<T extends DomainEvent> {
    /**
     * Event class to which this subscriber subscribes.
     */
    Class<T> subscribedTo();

    /**
     * Handling logic when the implement arrives.
     */
    void handle(T event);
}
