package art.gatoartstudio.synchronizedinstances;

import art.gatoartstudio.synchronizedinstances.config.ConfigManager;
import art.gatoartstudio.synchronizedinstances.helpers.Log;
import art.gatoartstudio.synchronizedinstances.subscribers.SubscribeListeners;
import org.bukkit.plugin.java.JavaPlugin;

public final class SynchronizedInstances extends JavaPlugin {
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        // Register event listeners
        SubscribeListeners.register(this);

        configManager = new ConfigManager(this);

        // Initialize configuration manager
        try {
            configManager.init();
        } catch (Exception e) {
            Log.error(e);
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        // Unregister event listeners
        SubscribeListeners.unregister();

        // Save configuration before shutdown
        configManager.save();
    }

    public static JavaPlugin getInstance() {
        return JavaPlugin.getPlugin(SynchronizedInstances.class);
    }
}
