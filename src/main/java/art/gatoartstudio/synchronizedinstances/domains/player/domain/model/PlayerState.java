package art.gatoartstudio.synchronizedinstances.domains.player.domain.model;

import art.gatoartstudio.synchronizedinstances.domains.player.domain.valueobjects.PotionEffectData;
import org.bukkit.GameMode;

import java.util.List;

public record PlayerState(
        int gameMode,
        double health,
        int food,
        float saturation,
        float experience,
        int level,
        int fireTicks,
        List<PotionEffectData> potionEffects
) {
    public PlayerState {
        if (health < 0)
            throw new IllegalArgumentException("Health cannot be negative");

        if (food < 0 || food > 20)
            throw new IllegalArgumentException("Food must be between 0 and 20");

        if (saturation < 0)
            throw new IllegalArgumentException("Saturation cannot be negative");

        if (experience < 0)
            throw new IllegalArgumentException("Experience cannot be negative");

        if (level < 0)
            throw new IllegalArgumentException("Level cannot be negative");

        if (fireTicks < 0)
            throw new IllegalArgumentException("FireTicks cannot be negative");

        if (potionEffects == null)
            throw new IllegalArgumentException("PotionEffects cannot be null");

        if (gameMode < 0 || gameMode >= GameMode.values().length)
            throw new IllegalArgumentException("Invalid game mode value");
    }
}
