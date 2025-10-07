package art.gatoartstudio.synchronizedinstances.config;

import art.gatoartstudio.synchronizedinstances.core.abstracts.ConfigPlugin;
import art.gatoartstudio.synchronizedinstances.models.ConfigModel;
import art.gatoartstudio.synchronizedinstances.models.mongo.ConfigMongoModel;
import art.gatoartstudio.synchronizedinstances.models.options.ConfigSaveOptionsModel;
import art.gatoartstudio.synchronizedinstances.models.options.OptionsEnabledFieldsModel;
import art.gatoartstudio.synchronizedinstances.models.redis.ConfigRedisModel;
import art.gatoartstudio.synchronizedinstances.models.redis.RedisTogglesModel;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public class ConfigManager extends ConfigPlugin {

    // These are the current plugin settings.
    private ConfigModel configModel = null;

    public ConfigManager(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public void load() {
        ConfigMongoModel configMongoModel = null;
        ConfigSaveOptionsModel configSaveOptionsModel = null;
        ConfigRedisModel configRedisModel = null;

        if (config.contains("mongodb")) {
            configMongoModel = new ConfigMongoModel(
                    config.getString("mongodb.uri"),
                    config.getString("mongodb.database"),
                    config.getString("mongodb.collection")
            );
        }

        if (config.contains("save-options")) {
            OptionsEnabledFieldsModel optionsEnabledFieldsModel = new OptionsEnabledFieldsModel(
                    config.getBoolean("save-options.enabled-fields.ip", false)
            );
            configSaveOptionsModel = new ConfigSaveOptionsModel(
                    config.getInt("save-options.save-interval-seconds", 60),
                    optionsEnabledFieldsModel
            );
        }

        if (config.contains("redis")) {
            RedisTogglesModel redisTogglesModel = new RedisTogglesModel(
                    config.getBoolean("redis.toggles.sync-players", true),
                    config.getBoolean("redis.toggles.pubsub-events", false)
            );

            configRedisModel = new ConfigRedisModel(
                    config.getBoolean("redis.enabled", false),
                    config.getString("redis.host", "127.0.0.1"),
                    config.getInt("redis.port", 6379),
                    config.getString("redis.password", ""),
                    redisTogglesModel
            );
        }

        this.configModel = new ConfigModel(
                configMongoModel,
                configSaveOptionsModel,
                Optional.ofNullable(configRedisModel)
        );
    }

    @Override
    public void init() {
        saveDefaults();
        load();
    }

    @Override
    public void save() {
        if (configModel == null) return;

        // MongoDB Config
        if (configModel.configMongo() != null) {
            config.set("mongodb.uri", configModel.configMongo().uri());
            config.set("mongodb.database", configModel.configMongo().database());
            config.set("mongodb.collection", configModel.configMongo().collection());
        }

        // Save Options Config
        if (configModel.configSaveOptions() != null) {
            config.set("save-options.save-interval-seconds", configModel.configSaveOptions().saveIntervalSeconds());
            if (configModel.configSaveOptions().optionsEnabledFields() != null) {
                config.set("save-options.enabled-fields.ip", configModel.configSaveOptions().optionsEnabledFields().ip());
            }
        }

        // Redis Config
        if (configModel.configRedis().isPresent()) {
            ConfigRedisModel redisConfig = configModel.configRedis().get();
            config.set("redis.enabled", redisConfig.enabled());
            config.set("redis.host", redisConfig.host());
            config.set("redis.port", redisConfig.port());
            config.set("redis.password", redisConfig.password());
            if (redisConfig.redisToggles() != null) {
                config.set("redis.toggles.sync-players", redisConfig.redisToggles().syncPlayers());
                config.set("redis.toggles.pubsub-events", redisConfig.redisToggles().pubSubEvents());
            }
        }

        super.save();
    }

    /**
     * Returns the current configuration model.
     * @return the current configuration model
     */
    public ConfigModel getConfigModel() {
        return configModel;
    }
}
