package art.gatoartstudio.synchronizedinstances.subscribers;

import art.gatoartstudio.synchronizedinstances.listeners.*;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class SubscribeListeners {
    private final static List<Listener> listeners = new ArrayList<>();

    /** Register all event listeners
     * @param plugin The main plugin instance
     */
    public static void register(JavaPlugin plugin) {
        PluginManager pluginManager = plugin.getServer().getPluginManager();

        listeners.add(new ChatListener());
        listeners.add(new PlayerDeathListener());
        listeners.add(new PlayerJoinListener());
        listeners.add(new PlayerQuitListener());
        listeners.add(new PlayerRespawnListener());

        listeners.forEach(listener -> pluginManager.registerEvents(listener, plugin));
    }

    /**
     * Unregister all event listeners
     */
    public static void unregister() {
        listeners.forEach(HandlerList::unregisterAll);
        listeners.clear();
    }
}
