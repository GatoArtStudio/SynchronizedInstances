package art.gatoartstudio.synchronizedinstances.domains.player.domain.event.implement;

import art.gatoartstudio.synchronizedinstances.domains.player.domain.event.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public record PlayerJoinedEvent(
        UUID playerId,
        String playerName,
        Instant timeCalledEvent
) implements DomainEvent {
    public PlayerJoinedEvent {
        if (playerId == null)
            throw new IllegalArgumentException("Player ID cannot be null");

        if (playerName == null || playerName.isEmpty())
            throw new IllegalArgumentException("Player name cannot be null or empty");

        if (timeCalledEvent == null)
            throw new IllegalArgumentException("Time called implement cannot be null");
    }
}