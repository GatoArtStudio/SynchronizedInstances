package art.gatoartstudio.synchronizedinstances.domains.player.infrastructure.subscribers.implement;

import art.gatoartstudio.synchronizedinstances.domains.player.application.ports.in.DomainEventSubscriber;
import art.gatoartstudio.synchronizedinstances.domains.player.domain.event.implement.PlayerQuitEvent;

public class PlayerQuitSubscribe implements DomainEventSubscriber<PlayerQuitEvent> {
    @Override
    public Class<PlayerQuitEvent> subscribedTo() {
        return PlayerQuitEvent.class;
    }

    @Override
    public void handle(PlayerQuitEvent event) {
        // TODO: Implement logic to handle player quitting the server
    }
}
