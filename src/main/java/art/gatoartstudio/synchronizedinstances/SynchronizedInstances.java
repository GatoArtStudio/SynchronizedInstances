package art.gatoartstudio.synchronizedinstances;

import art.gatoartstudio.synchronizedinstances.config.ConfigManager;
import art.gatoartstudio.synchronizedinstances.domains.player.infrastructure.enums.TypeBus;
import art.gatoartstudio.synchronizedinstances.domains.player.infrastructure.enums.TypeRepository;
import art.gatoartstudio.synchronizedinstances.domains.player.infrastructure.eventbus.BusFactory;
import art.gatoartstudio.synchronizedinstances.domains.player.infrastructure.persistence.PlayerRepositoryFactory;
import art.gatoartstudio.synchronizedinstances.domains.player.infrastructure.subscribers.SubscribeDomainEvents;
import art.gatoartstudio.synchronizedinstances.helpers.Log;
import art.gatoartstudio.synchronizedinstances.subscribers.SubscribeListeners;
import org.bukkit.plugin.java.JavaPlugin;

public final class SynchronizedInstances extends JavaPlugin {
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        // Initialize configuration manager
        configManager = new ConfigManager(this);

        // Initialize configuration manager
        try {
            configManager.init();
        } catch (Exception e) {
            Log.error(e);
            getServer().getPluginManager().disablePlugin(this);
        }

        // Initialize implement bus
        BusFactory.getInstance(TypeBus.IN_MEMORY, configManager);
        PlayerRepositoryFactory.getInstance(TypeRepository.MONGODB, configManager);

        // Register implement listeners
        SubscribeListeners.register(this);
        SubscribeDomainEvents.register(BusFactory.getInstance().createBus());
    }

    @Override
    public void onDisable() {
        // Unregister implement listeners
        SubscribeListeners.unregister();
        SubscribeDomainEvents.unregister(BusFactory.getInstance().createBus());

        // Save configuration before shutdown
        configManager.save();
    }

    public static JavaPlugin getInstance() {
        return JavaPlugin.getPlugin(SynchronizedInstances.class);
    }
}
