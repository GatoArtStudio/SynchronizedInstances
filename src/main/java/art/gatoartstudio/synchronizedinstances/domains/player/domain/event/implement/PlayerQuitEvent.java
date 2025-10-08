package art.gatoartstudio.synchronizedinstances.domains.player.domain.event.implement;

import art.gatoartstudio.synchronizedinstances.domains.player.domain.event.DomainEvent;
import art.gatoartstudio.synchronizedinstances.domains.player.domain.model.PlayerInventory;
import art.gatoartstudio.synchronizedinstances.domains.player.domain.model.PlayerState;
import art.gatoartstudio.synchronizedinstances.domains.player.domain.valueobjects.PlayerLocation;

import java.time.Instant;
import java.util.UUID;

public record PlayerQuitEvent(
        UUID playerId,
        String playerName,
        Instant timeCalledEvent,
        PlayerInventory inventory,
        PlayerState state,
        PlayerLocation lastLocation
) implements DomainEvent {
    public PlayerQuitEvent {
        if (playerId == null)
            throw new IllegalArgumentException("Player ID cannot be null");

        if (playerName == null || playerName.isEmpty())
            throw new IllegalArgumentException("Player name cannot be null or empty");

        if (timeCalledEvent == null)
            throw new IllegalArgumentException("Time called implement cannot be null");

        if (inventory == null)
            throw new IllegalArgumentException("Inventory cannot be null");

        if (state == null)
            throw new IllegalArgumentException("State cannot be null");

        if (lastLocation == null)
            throw new IllegalArgumentException("Last location cannot be null");
    }
}