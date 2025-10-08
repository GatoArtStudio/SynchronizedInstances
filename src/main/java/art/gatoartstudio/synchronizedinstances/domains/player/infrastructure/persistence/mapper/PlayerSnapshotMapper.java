package art.gatoartstudio.synchronizedinstances.domains.player.infrastructure.persistence.mapper;

import art.gatoartstudio.synchronizedinstances.domains.player.domain.aggregate.PlayerSnapshot;
import art.gatoartstudio.synchronizedinstances.domains.player.domain.model.*;
import art.gatoartstudio.synchronizedinstances.domains.player.domain.valueobjects.PlayerLocation;
import art.gatoartstudio.synchronizedinstances.domains.player.domain.valueobjects.PotionEffectData;
import art.gatoartstudio.synchronizedinstances.domains.player.infrastructure.persistence.dto.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Mapper class to convert between domain objects and DTOs for MongoDB persistence.
 */
public class PlayerSnapshotMapper {

    /**
     * Converts a PlayerSnapshot domain object to a PlayerSnapshotDto for MongoDB storage.
     */
    public static PlayerSnapshotDto toDto(PlayerSnapshot playerSnapshot) {
        if (playerSnapshot == null) {
            return null;
        }

        PlayerSnapshotDto dto = new PlayerSnapshotDto(
                playerSnapshot.getProfile().uuid().toString(),
                toDto(playerSnapshot.getProfile())
        );

        if (playerSnapshot.getState() != null) {
            dto.setState(toDto(playerSnapshot.getState()));
        }

        if (playerSnapshot.getInventory() != null) {
            dto.setInventory(toDto(playerSnapshot.getInventory()));
        }

        if (playerSnapshot.getStatistics() != null) {
            dto.setStatistics(toDto(playerSnapshot.getStatistics()));
        }

        if (playerSnapshot.getLastLocation() != null) {
            dto.setLastLocation(toDto(playerSnapshot.getLastLocation()));
        }

        if (playerSnapshot.getBedSpawnLocation() != null) {
            dto.setBedSpawnLocation(toDto(playerSnapshot.getBedSpawnLocation()));
        }

        return dto;
    }

    /**
     * Converts a PlayerSnapshotDto from MongoDB to a PlayerSnapshot domain object.
     */
    public static PlayerSnapshot fromDto(PlayerSnapshotDto dto) {
        if (dto == null) {
            return null;
        }

        PlayerProfile profile = fromDto(dto.getProfile());
        PlayerSnapshot playerSnapshot = new PlayerSnapshot(profile);

        try {
            if (dto.getState() != null) {
                playerSnapshot.updateState(fromDto(dto.getState()));
            }

            if (dto.getInventory() != null) {
                playerSnapshot.updateInventory(fromDto(dto.getInventory()));
            }

            if (dto.getStatistics() != null) {
                playerSnapshot.updateStatistics(fromDto(dto.getStatistics()));
            }

            if (dto.getLastLocation() != null) {
                playerSnapshot.updateLastLocation(fromDto(dto.getLastLocation()));
            }

            if (dto.getBedSpawnLocation() != null) {
                playerSnapshot.updateBedSpawnLocation(fromDto(dto.getBedSpawnLocation()));
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Error converting PlayerSnapshotDto to PlayerSnapshot", e);
        }

        return playerSnapshot;
    }

    // PlayerProfile mappers
    public static PlayerProfileDto toDto(PlayerProfile profile) {
        if (profile == null) return null;
        return new PlayerProfileDto(
                profile.uuid().toString(),
                profile.name(),
                profile.firstJoin(),
                profile.lastSeen()
        );
    }

    public static PlayerProfile fromDto(PlayerProfileDto dto) {
        if (dto == null) return null;
        return new PlayerProfile(
                UUID.fromString(dto.getUuid()),
                dto.getName(),
                dto.getFirstJoin(),
                dto.getLastSeen()
        );
    }

    // PlayerState mappers
    public static PlayerStateDto toDto(PlayerState state) {
        if (state == null) return null;
        return new PlayerStateDto(
                state.gameMode(),
                state.health(),
                state.food(),
                state.saturation(),
                state.experience(),
                state.level(),
                state.fireTicks(),
                state.potionEffects().stream()
                        .map(PlayerSnapshotMapper::toDto)
                        .collect(Collectors.toList())
        );
    }

    public static PlayerState fromDto(PlayerStateDto dto) {
        if (dto == null) return null;
        return new PlayerState(
                dto.getGameMode(),
                dto.getHealth(),
                dto.getFood(),
                dto.getSaturation(),
                dto.getExperience(),
                dto.getLevel(),
                dto.getFireTicks(),
                dto.getPotionEffects().stream()
                        .map(PlayerSnapshotMapper::fromDto)
                        .collect(Collectors.toList())
        );
    }

    // PlayerInventory mappers
    public static PlayerInventoryDto toDto(PlayerInventory inventory) {
        if (inventory == null) return null;
        return new PlayerInventoryDto(
                inventory.inventory().orElse(null),
                inventory.armor().orElse(null),
                inventory.enderChest().orElse(null)
        );
    }

    public static PlayerInventory fromDto(PlayerInventoryDto dto) {
        if (dto == null) return null;
        return new PlayerInventory(
                Optional.ofNullable(dto.getInventory()),
                Optional.ofNullable(dto.getArmor()),
                Optional.ofNullable(dto.getEnderChest())
        );
    }

    // PlayerStatistics mappers
    public static PlayerStatisticsDto toDto(PlayerStatistics statistics) {
        if (statistics == null) return null;
        return new PlayerStatisticsDto(
                statistics.playTime(),
                statistics.deaths(),
                statistics.kills()
        );
    }

    public static PlayerStatistics fromDto(PlayerStatisticsDto dto) {
        if (dto == null) return null;
        return new PlayerStatistics(
                dto.getPlayTime(),
                dto.getDeaths(),
                dto.getKills()
        );
    }

    // PlayerLocation mappers
    public static PlayerLocationDto toDto(PlayerLocation location) {
        if (location == null) return null;
        return new PlayerLocationDto(
                location.worldUUID().toString(),
                location.x(),
                location.y(),
                location.z(),
                location.yaw(),
                location.pitch()
        );
    }

    public static PlayerLocation fromDto(PlayerLocationDto dto) {
        if (dto == null) return null;
        return new PlayerLocation(
                UUID.fromString(dto.getWorldUUID()),
                dto.getX(),
                dto.getY(),
                dto.getZ(),
                dto.getYaw(),
                dto.getPitch()
        );
    }

    // PotionEffectData mappers
    public static PotionEffectDataDto toDto(PotionEffectData effect) {
        if (effect == null) return null;
        return new PotionEffectDataDto(
                effect.type(),
                effect.duration(),
                effect.amplifier(),
                effect.particles()
        );
    }

    public static PotionEffectData fromDto(PotionEffectDataDto dto) {
        if (dto == null) return null;
        return new PotionEffectData(
                dto.getType(),
                dto.getDuration(),
                dto.getAmplifier(),
                dto.isParticles()
        );
    }
}