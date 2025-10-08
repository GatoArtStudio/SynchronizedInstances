package art.gatoartstudio.synchronizedinstances.domains.player.domain.model;

import java.time.Instant;
import java.util.UUID;

public record PlayerProfile(
        UUID uuid,
        String name,
        Instant firstJoin,
        Instant lastSeen
) {
    public PlayerProfile {
        if (uuid == null) {
            throw new IllegalArgumentException("UUID cannot be null");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (firstJoin == null) {
            throw new IllegalArgumentException("FirstJoin cannot be null");
        }
        if (lastSeen == null) {
            throw new IllegalArgumentException("LastSeen cannot be null");
        }
    }
}
