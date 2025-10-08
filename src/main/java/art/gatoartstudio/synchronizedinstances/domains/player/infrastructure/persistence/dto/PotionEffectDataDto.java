package art.gatoartstudio.synchronizedinstances.domains.player.infrastructure.persistence.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PotionEffectDataDto {
    
    @JsonProperty("type")
    private String type;
    
    @JsonProperty("duration")
    private int duration;
    
    @JsonProperty("amplifier")
    private int amplifier;
    
    @JsonProperty("particles")
    private boolean particles;

    // Default constructor for Jackson
    public PotionEffectDataDto() {}

    public PotionEffectDataDto(String type, int duration, int amplifier, boolean particles) {
        this.type = type;
        this.duration = duration;
        this.amplifier = amplifier;
        this.particles = particles;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getAmplifier() {
        return amplifier;
    }

    public void setAmplifier(int amplifier) {
        this.amplifier = amplifier;
    }

    public boolean isParticles() {
        return particles;
    }

    public void setParticles(boolean particles) {
        this.particles = particles;
    }
}