package art.gatoartstudio.synchronizedinstances.domains.player.infrastructure.subscribers.implement;

import art.gatoartstudio.synchronizedinstances.domains.player.application.ports.in.DomainEventSubscriber;
import art.gatoartstudio.synchronizedinstances.domains.player.domain.event.implement.PlayerChatEvent;

public class PlayerChatSubscribe implements DomainEventSubscriber<PlayerChatEvent> {
    @Override
    public Class<PlayerChatEvent> subscribedTo() {
        return PlayerChatEvent.class;
    }

    @Override
    public void handle(PlayerChatEvent event) {
        // TODO: Implement logic to handle player chat events
    }
}
