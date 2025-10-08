package art.gatoartstudio.synchronizedinstances.domains.player.infrastructure.persistence.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PlayerStateDto {
    
    @JsonProperty("gameMode")
    private int gameMode;
    
    @JsonProperty("health")
    private double health;
    
    @JsonProperty("food")
    private int food;
    
    @JsonProperty("saturation")
    private float saturation;
    
    @JsonProperty("experience")
    private float experience;
    
    @JsonProperty("level")
    private int level;
    
    @JsonProperty("fireTicks")
    private int fireTicks;
    
    @JsonProperty("potionEffects")
    private List<PotionEffectDataDto> potionEffects;

    // Default constructor for Jackson
    public PlayerStateDto() {}

    public PlayerStateDto(int gameMode, double health, int food, float saturation,
                         float experience, int level, int fireTicks,
                         List<PotionEffectDataDto> potionEffects) {
        this.gameMode = gameMode;
        this.health = health;
        this.food = food;
        this.saturation = saturation;
        this.experience = experience;
        this.level = level;
        this.fireTicks = fireTicks;
        this.potionEffects = potionEffects;
    }

    public int getGameMode() {
        return gameMode;
    }

    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public float getSaturation() {
        return saturation;
    }

    public void setSaturation(float saturation) {
        this.saturation = saturation;
    }

    public float getExperience() {
        return experience;
    }

    public void setExperience(float experience) {
        this.experience = experience;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getFireTicks() {
        return fireTicks;
    }

    public void setFireTicks(int fireTicks) {
        this.fireTicks = fireTicks;
    }

    public List<PotionEffectDataDto> getPotionEffects() {
        return potionEffects;
    }

    public void setPotionEffects(List<PotionEffectDataDto> potionEffects) {
        this.potionEffects = potionEffects;
    }
}