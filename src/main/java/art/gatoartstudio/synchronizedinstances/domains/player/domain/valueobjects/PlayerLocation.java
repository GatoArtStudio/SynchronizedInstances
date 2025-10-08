package art.gatoartstudio.synchronizedinstances.domains.player.domain.valueobjects;

import java.util.UUID;

public record PlayerLocation(
        UUID worldUUID,
        double x,
        double y,
        double z,
        float yaw,
        float pitch
) {
    public PlayerLocation {
        if (worldUUID == null)
            throw new IllegalArgumentException("World UUID cannot be null");
    }
}
