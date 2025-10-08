package art.gatoartstudio.synchronizedinstances.domains.player.domain.repository;

import art.gatoartstudio.synchronizedinstances.domains.player.domain.aggregate.PlayerSnapshot;

import java.util.Optional;
import java.util.UUID;

public interface PlayerRepository {
    Optional<PlayerSnapshot> findById(UUID uuid);
    void save(PlayerSnapshot playerSnapshot);
    void delete(UUID uuid);
}
