package art.gatoartstudio.synchronizedinstances.domains.player.domain.aggregate;

import art.gatoartstudio.synchronizedinstances.domains.player.domain.model.PlayerInventory;
import art.gatoartstudio.synchronizedinstances.domains.player.domain.model.PlayerProfile;
import art.gatoartstudio.synchronizedinstances.domains.player.domain.model.PlayerState;
import art.gatoartstudio.synchronizedinstances.domains.player.domain.model.PlayerStatistics;
import art.gatoartstudio.synchronizedinstances.domains.player.domain.valueobjects.PlayerLocation;

public class PlayerSnapshot {
    private final PlayerProfile profile;
    private PlayerState state;
    private PlayerInventory inventory;
    private PlayerStatistics statistics;
    private PlayerLocation lastLocation;
    private PlayerLocation bedSpawnLocation;

    public PlayerSnapshot(PlayerProfile profile) {
        this.profile = profile;
    }

    public PlayerProfile getProfile() {
        return profile;
    }

    public PlayerState getState() {
        return state;
    }

    public PlayerInventory getInventory() {
        return inventory;
    }

    public PlayerStatistics getStatistics() {
        return statistics;
    }

    public PlayerLocation getLastLocation() {
        return lastLocation;
    }

    public PlayerLocation getBedSpawnLocation() {
        return bedSpawnLocation;
    }

    public void updateState(PlayerState state) throws IllegalArgumentException {
        if (state == null)
            throw new IllegalArgumentException("State cannot be null");

        this.state = state;
    }

    public void updateInventory(PlayerInventory inventory) throws IllegalArgumentException {
        if (inventory == null)
            throw new IllegalArgumentException("Inventory cannot be null");

        this.inventory = inventory;
    }

    public void updateStatistics(PlayerStatistics statistics) throws IllegalArgumentException {
        if (statistics == null)
            throw new IllegalArgumentException("Statistics cannot be null");

        this.statistics = statistics;
    }

    public void updateLastLocation(PlayerLocation location) throws IllegalArgumentException {
        if (location == null)
            throw new IllegalArgumentException("Location cannot be null");

        this.lastLocation = location;
    }

    public void updateBedSpawnLocation(PlayerLocation location) throws IllegalArgumentException {
        if (location == null)
            throw new IllegalArgumentException("Location cannot be null");

        this.bedSpawnLocation = location;
    }
}
