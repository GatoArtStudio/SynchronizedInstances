package art.gatoartstudio.synchronizedinstances.domains.player.domain.model;

public record PlayerStatistics(
        long playTime,
        int deaths,
        int kills
) {
    public PlayerStatistics {
        if (playTime < 0)
            throw new IllegalArgumentException("Play time cannot be negative");

        if (deaths < 0)
            throw new IllegalArgumentException("Deaths cannot be negative");

        if (kills < 0)
            throw new IllegalArgumentException("Kills cannot be negative");
    }
}
