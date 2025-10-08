package art.gatoartstudio.synchronizedinstances.domains.player.infrastructure.eventbus;

import art.gatoartstudio.synchronizedinstances.config.ConfigManager;
import art.gatoartstudio.synchronizedinstances.domains.player.domain.event.DomainEventBus;
import art.gatoartstudio.synchronizedinstances.domains.player.infrastructure.enums.TypeBus;
import art.gatoartstudio.synchronizedinstances.domains.player.infrastructure.eventbus.buses.InMemoryEventBus;

import java.util.concurrent.ConcurrentHashMap;

public final class BusFactory {
    private static volatile BusFactory instance;

    private TypeBus typeBus;
    private ConfigManager configManager;
    private ConcurrentHashMap<TypeBus, DomainEventBus> buses = new ConcurrentHashMap<>();

    private BusFactory(TypeBus type, ConfigManager configManager) {
        this.typeBus = type;
        this.configManager = configManager;

        // Initialize different types of buses if needed
        InMemoryEventBus inMemoryEventBus = new InMemoryEventBus();
        buses.put(TypeBus.DEFAULT, inMemoryEventBus);
        buses.put(TypeBus.IN_MEMORY, inMemoryEventBus);
    }

    public static BusFactory getInstance() {
        return getInstance(null, null);
    }

    public static BusFactory getInstance(TypeBus type, ConfigManager configManager) throws IllegalStateException {
        BusFactory result = instance;

        if (result != null)
            return result;

        synchronized (BusFactory.class) {
            if (instance == null) {
                if (type == null || configManager == null) {
                    throw new IllegalStateException("BusFactory is not initialized. TypeBus and ConfigManager are required.");
                }
                instance = new BusFactory(type, configManager);
            }
            return instance;
        }
    }

    /**
     * Create a bus based on the type specified during initialization.
     * @return An instance of DomainEventBus.
     */
    public DomainEventBus createBus() {
        return buses.get(typeBus);
    }
}
