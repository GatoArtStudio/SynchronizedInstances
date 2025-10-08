package art.gatoartstudio.synchronizedinstances.domains.player.infrastructure.persistence.repositories;

import art.gatoartstudio.synchronizedinstances.domains.player.domain.aggregate.PlayerSnapshot;
import art.gatoartstudio.synchronizedinstances.domains.player.domain.repository.PlayerRepository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryRepository implements PlayerRepository {
    private final Map<UUID, PlayerSnapshot> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<PlayerSnapshot> findById(UUID uuid) {
        if (uuid == null)
            return Optional.empty();

        return storage.containsKey(uuid) ? Optional.of(storage.get(uuid)) : Optional.empty();
    }

    @Override
    public void save(PlayerSnapshot playerSnapshot) {
        if (playerSnapshot == null || playerSnapshot.getProfile().uuid() == null)
            return;

        storage.put(playerSnapshot.getProfile().uuid(), playerSnapshot);
    }

    @Override
    public void delete(UUID uuid) {
        if (uuid == null)
            return;

        storage.remove(uuid);
    }
}
