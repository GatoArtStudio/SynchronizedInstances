package art.gatoartstudio.synchronizedinstances.domains.player.domain.event.implement;

import art.gatoartstudio.synchronizedinstances.domains.player.domain.event.DomainEvent;

import java.util.UUID;

public record PlayerChatEvent(
        UUID playerId,
        String playerName,
        String message
) implements DomainEvent {
    public PlayerChatEvent {
        if (playerId == null)
            throw new IllegalArgumentException("Player ID cannot be null");

        if (playerName == null || playerName.isEmpty())
            throw new IllegalArgumentException("Player name cannot be null or empty");

        if (message == null || message.isEmpty())
            throw new IllegalArgumentException("Message cannot be null");
    }
}
