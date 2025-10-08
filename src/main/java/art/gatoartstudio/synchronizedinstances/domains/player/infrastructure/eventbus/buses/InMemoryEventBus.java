package art.gatoartstudio.synchronizedinstances.domains.player.infrastructure.eventbus.buses;

import art.gatoartstudio.synchronizedinstances.domains.player.application.ports.in.DomainEventSubscriber;
import art.gatoartstudio.synchronizedinstances.domains.player.domain.event.DomainEvent;
import art.gatoartstudio.synchronizedinstances.domains.player.domain.event.DomainEventBus;

import java.util.ArrayList;
import java.util.List;

public class InMemoryEventBus implements DomainEventBus {
    private final List<DomainEventSubscriber<? extends DomainEvent>> subscribers = new ArrayList<>();

    @Override
    public void publish(DomainEvent event) {
        subscribers.forEach(domainEventSubscriber -> {
            if (domainEventSubscriber.subscribedTo().equals(event.getClass())) {
                @SuppressWarnings("unchecked")
                DomainEventSubscriber<DomainEvent> sub = (DomainEventSubscriber<DomainEvent>) domainEventSubscriber;
                sub.handle(event);
            }
        });
    }

    @Override
    public <T extends DomainEvent> void subscribe(DomainEventSubscriber<T> subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public <T extends DomainEvent> void unsubscribe(DomainEventSubscriber<T> subscriber) {
        subscribers.remove(subscriber);
    }
}
