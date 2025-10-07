package art.gatoartstudio.synchronizedinstances.models;

import art.gatoartstudio.synchronizedinstances.models.mongo.ConfigMongoModel;
import art.gatoartstudio.synchronizedinstances.models.options.ConfigSaveOptionsModel;
import art.gatoartstudio.synchronizedinstances.models.redis.ConfigRedisModel;

import java.util.Optional;

public record ConfigModel(ConfigMongoModel configMongo, ConfigSaveOptionsModel configSaveOptions, Optional<ConfigRedisModel> configRedis) {
    public ConfigModel {
        if (configMongo == null) {
            throw new IllegalArgumentException("ConfigMongoModel cannot be null");
        }
        if (configSaveOptions == null) {
            throw new IllegalArgumentException("ConfigSaveOptionsModel cannot be null");
        }
    }
}
