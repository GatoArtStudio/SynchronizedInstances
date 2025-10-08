package art.gatoartstudio.synchronizedinstances.domains.player.infrastructure.persistence.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class PlayerProfileDto {
    
    @JsonProperty("uuid")
    private String uuid;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("firstJoin")
    private Instant firstJoin;
    
    @JsonProperty("lastSeen")
    private Instant lastSeen;

    // Default constructor for Jackson
    public PlayerProfileDto() {}

    public PlayerProfileDto(String uuid, String name, Instant firstJoin, Instant lastSeen) {
        this.uuid = uuid;
        this.name = name;
        this.firstJoin = firstJoin;
        this.lastSeen = lastSeen;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getFirstJoin() {
        return firstJoin;
    }

    public void setFirstJoin(Instant firstJoin) {
        this.firstJoin = firstJoin;
    }

    public Instant getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Instant lastSeen) {
        this.lastSeen = lastSeen;
    }
}