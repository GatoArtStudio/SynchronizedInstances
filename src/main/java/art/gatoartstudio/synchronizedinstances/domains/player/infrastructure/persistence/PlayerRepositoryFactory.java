package art.gatoartstudio.synchronizedinstances.domains.player.infrastructure.persistence;

import art.gatoartstudio.synchronizedinstances.config.ConfigManager;
import art.gatoartstudio.synchronizedinstances.domains.player.domain.repository.PlayerRepository;
import art.gatoartstudio.synchronizedinstances.domains.player.infrastructure.enums.TypeRepository;
import art.gatoartstudio.synchronizedinstances.domains.player.infrastructure.persistence.repositories.InMemoryRepository;
import art.gatoartstudio.synchronizedinstances.domains.player.infrastructure.persistence.repositories.MongoDbRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class PlayerRepositoryFactory {
    private static volatile PlayerRepositoryFactory instance;

    private TypeRepository typeRepository;
    private ConfigManager configManager;
    private final Map<TypeRepository, PlayerRepository> repositories = new ConcurrentHashMap<>();

    private PlayerRepositoryFactory(TypeRepository typeRepository, ConfigManager configManager) {
        this.typeRepository = typeRepository;
        this.configManager = configManager;

        // Initialize different types of repositories if needed
        MongoDbRepository mongoDbRepository = new MongoDbRepository(configManager.getConfigModel().configMongo());
        repositories.put(TypeRepository.DEFAULT, mongoDbRepository);
        repositories.put(TypeRepository.MONGODB, mongoDbRepository);
        repositories.put(TypeRepository.IN_MEMORY, new InMemoryRepository());
    }

    public static PlayerRepositoryFactory getInstance() {
        return getInstance(null, null);
    }

    public static PlayerRepositoryFactory getInstance(TypeRepository typeRepository, ConfigManager configManager) {
        PlayerRepositoryFactory result = instance;

        if (result != null)
            return result;

        synchronized (PlayerRepositoryFactory.class) {
            if (instance == null) {
                if (typeRepository == null || configManager == null) {
                    throw new IllegalStateException("PlayerRepositoryFactory is not initialized. TypeRepository and ConfigManager are required.");
                }
                instance = new PlayerRepositoryFactory(typeRepository, configManager);
            }
            return instance;
        }
    }

    /**
     * Create a repository based on the type specified during initialization.
     * @return An instance of PlayerRepository.
     */
    public PlayerRepository createRepository() {
        return repositories.get(typeRepository);
    }
}
