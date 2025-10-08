package art.gatoartstudio.synchronizedinstances.domains.player.infrastructure.persistence.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerLocationDto {
    
    @JsonProperty("worldUUID")
    private String worldUUID;
    
    @JsonProperty("x")
    private double x;
    
    @JsonProperty("y")
    private double y;
    
    @JsonProperty("z")
    private double z;
    
    @JsonProperty("yaw")
    private float yaw;
    
    @JsonProperty("pitch")
    private float pitch;

    // Default constructor for Jackson
    public PlayerLocationDto() {}

    public PlayerLocationDto(String worldUUID, double x, double y, double z, float yaw, float pitch) {
        this.worldUUID = worldUUID;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public String getWorldUUID() {
        return worldUUID;
    }

    public void setWorldUUID(String worldUUID) {
        this.worldUUID = worldUUID;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
}