package art.gatoartstudio.synchronizedinstances.domains.player.infrastructure.subscribers;

import art.gatoartstudio.synchronizedinstances.domains.player.application.ports.in.DomainEventSubscriber;
import art.gatoartstudio.synchronizedinstances.domains.player.domain.event.DomainEvent;
import art.gatoartstudio.synchronizedinstances.domains.player.domain.event.DomainEventBus;
import art.gatoartstudio.synchronizedinstances.domains.player.infrastructure.subscribers.implement.PlayerChatSubscribe;
import art.gatoartstudio.synchronizedinstances.domains.player.infrastructure.subscribers.implement.PlayerJoinedSubscribe;
import art.gatoartstudio.synchronizedinstances.domains.player.infrastructure.subscribers.implement.PlayerQuitSubscribe;

import java.util.ArrayList;
import java.util.List;

public class SubscribeDomainEvents {
    private final static List<DomainEventSubscriber<? extends DomainEvent>> subscribers = new ArrayList<>();

    public static void register(DomainEventBus bus) {
        subscribers.add(new PlayerJoinedSubscribe());
        subscribers.add(new PlayerQuitSubscribe());
        subscribers.add(new PlayerChatSubscribe());

        subscribers.forEach(bus::subscribe);
    }

    public static void unregister(DomainEventBus bus) {
        subscribers.forEach(bus::unsubscribe);
        subscribers.clear();
    }
}
