package art.gatoartstudio.synchronizedinstances.domains.player.infrastructure.persistence.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Data Transfer Object for PlayerSnapshot to be stored in MongoDB.
 * This class represents the serializable form of a PlayerSnapshot.
 */
public class PlayerSnapshotDto {
    
    @JsonProperty("_id")
    private ObjectId id;
    
    @JsonProperty("uuid")
    private String uuid;
    
    @JsonProperty("profile")
    private PlayerProfileDto profile;
    
    @JsonProperty("state")
    private PlayerStateDto state;
    
    @JsonProperty("inventory")
    private PlayerInventoryDto inventory;
    
    @JsonProperty("statistics")
    private PlayerStatisticsDto statistics;
    
    @JsonProperty("lastLocation")
    private PlayerLocationDto lastLocation;
    
    @JsonProperty("bedSpawnLocation")
    private PlayerLocationDto bedSpawnLocation;
    
    @JsonProperty("createdAt")
    private Instant createdAt;
    
    @JsonProperty("updatedAt")
    private Instant updatedAt;

    // Default constructor for Jackson
    public PlayerSnapshotDto() {}

    public PlayerSnapshotDto(String uuid, PlayerProfileDto profile) {
        this.uuid = uuid;
        this.profile = profile;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    // Getters and Setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public PlayerProfileDto getProfile() {
        return profile;
    }

    public void setProfile(PlayerProfileDto profile) {
        this.profile = profile;
    }

    public PlayerStateDto getState() {
        return state;
    }

    public void setState(PlayerStateDto state) {
        this.state = state;
    }

    public PlayerInventoryDto getInventory() {
        return inventory;
    }

    public void setInventory(PlayerInventoryDto inventory) {
        this.inventory = inventory;
    }

    public PlayerStatisticsDto getStatistics() {
        return statistics;
    }

    public void setStatistics(PlayerStatisticsDto statistics) {
        this.statistics = statistics;
    }

    public PlayerLocationDto getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(PlayerLocationDto lastLocation) {
        this.lastLocation = lastLocation;
    }

    public PlayerLocationDto getBedSpawnLocation() {
        return bedSpawnLocation;
    }

    public void setBedSpawnLocation(PlayerLocationDto bedSpawnLocation) {
        this.bedSpawnLocation = bedSpawnLocation;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void updateTimestamp() {
        this.updatedAt = Instant.now();
    }
}