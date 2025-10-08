package art.gatoartstudio.synchronizedinstances.domains.player.infrastructure.persistence.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerStatisticsDto {
    
    @JsonProperty("playTime")
    private long playTime;
    
    @JsonProperty("deaths")
    private int deaths;
    
    @JsonProperty("kills")
    private int kills;

    // Default constructor for Jackson
    public PlayerStatisticsDto() {}

    public PlayerStatisticsDto(long playTime, int deaths, int kills) {
        this.playTime = playTime;
        this.deaths = deaths;
        this.kills = kills;
    }

    public long getPlayTime() {
        return playTime;
    }

    public void setPlayTime(long playTime) {
        this.playTime = playTime;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }
}