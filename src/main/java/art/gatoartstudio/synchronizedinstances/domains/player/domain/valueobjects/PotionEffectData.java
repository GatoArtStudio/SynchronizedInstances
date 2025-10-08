package art.gatoartstudio.synchronizedinstances.domains.player.domain.valueobjects;

public record PotionEffectData(
        String type,
        int duration,
        int amplifier,
        boolean particles
) {
    public PotionEffectData {
        if (type == null || type.isEmpty())
            throw new IllegalArgumentException("Type cannot be null or empty");

        if (duration < 0)
            throw new IllegalArgumentException("Duration cannot be negative");

        if (amplifier < 0)
            throw new IllegalArgumentException("Amplifier cannot be negative");
    }
}
