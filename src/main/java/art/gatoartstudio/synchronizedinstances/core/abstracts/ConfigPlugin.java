package art.gatoartstudio.synchronizedinstances.core.abstracts;

import art.gatoartstudio.synchronizedinstances.helpers.Log;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class ConfigPlugin {
    protected JavaPlugin plugin;
    protected FileConfiguration config;

    public ConfigPlugin(JavaPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    /**
     * Saves the default configuration file if it does not already exist.
     * @return true if the default configuration file was created, false if it already exists or an error occurred
     */
    public boolean saveDefaults() {
        if (plugin.getDataFolder().exists()) return false;

        try {
            plugin.saveDefaultConfig();
            Log.info("Configuration file created successfully.");
            return true;
        } catch (Exception e) {
            Log.error("Error creating configuration file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Reloads the configuration from the configuration file.
     */
    public void reload() {
        plugin.reloadConfig();
        config = plugin.getConfig();
        load();
    }

    /**
     * Returns the configuration object.
     * @return the configuration object
     */
    public FileConfiguration getConfig() {
        return config;
    }

    /**
     * Saves the current configuration to the configuration file.
     * Logs success or error messages based on the outcome of the save operation.
     */
    public void save() {
        try {
            plugin.saveConfig();
        } catch (Exception e) {
            Log.error(e);
        }
    }

    /**
     * This method is called when the plugin is loaded and it is necessary to load the configuration.
     * It is called after the plugin has been enabled and before the plugin is fully initialized.
     */
    public abstract void load();

    /**
     * This method is called when the plugin is fully initialized and it is necessary to initialize
     * the configuration. It is called after the plugin has been enabled and after the plugin has been
     * loaded.
     */
    public abstract void init();
}
