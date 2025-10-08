package art.gatoartstudio.synchronizedinstances.domains.player.infrastructure.subscribers.implement;

import art.gatoartstudio.synchronizedinstances.domains.player.application.ports.in.DomainEventSubscriber;
import art.gatoartstudio.synchronizedinstances.domains.player.domain.event.implement.PlayerJoinedEvent;

public class PlayerJoinedSubscribe implements DomainEventSubscriber<PlayerJoinedEvent> {
    @Override
    public Class<PlayerJoinedEvent> subscribedTo() {
        return PlayerJoinedEvent.class;
    }

    @Override
    public void handle(PlayerJoinedEvent event) {
        // TODO: Implement logic to handle player joined implement
    }
}
