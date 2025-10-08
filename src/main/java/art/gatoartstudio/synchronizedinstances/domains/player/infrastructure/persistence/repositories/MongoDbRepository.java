package art.gatoartstudio.synchronizedinstances.domains.player.infrastructure.persistence.repositories;

import art.gatoartstudio.synchronizedinstances.domains.player.domain.aggregate.PlayerSnapshot;
import art.gatoartstudio.synchronizedinstances.domains.player.domain.repository.PlayerRepository;
import art.gatoartstudio.synchronizedinstances.models.mongo.ConfigMongoModel;

import java.util.Optional;
import java.util.UUID;

public class MongoDbRepository implements PlayerRepository {
    private ConfigMongoModel configMongoModel;

    public MongoDbRepository(ConfigMongoModel configMongoModel) {
        this.configMongoModel = configMongoModel;
    }

    @Override
    public Optional<PlayerSnapshot> findById(UUID uuid) {
        // TODO: Implement MongoDB logic to find player by UUID
        return Optional.empty();
    }

    @Override
    public void save(PlayerSnapshot playerSnapshot) {
        // TODO: Implement MongoDB logic to save player snapshot
    }

    @Override
    public void delete(UUID uuid) {
        // TODO: Implement MongoDB logic to delete player by UUID
    }
}
