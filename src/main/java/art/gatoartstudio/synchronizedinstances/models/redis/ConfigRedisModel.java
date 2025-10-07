package art.gatoartstudio.synchronizedinstances.models.redis;

public record ConfigRedisModel(boolean enabled, String host, int port, String password, RedisTogglesModel redisToggles) {
    public ConfigRedisModel {
        if (enabled) {
            if (host == null || host.isEmpty()) {
                throw new IllegalArgumentException("Redis host cannot be null or empty when Redis is enabled");
            }
            if (port <= 0 || port > 65535) {
                throw new IllegalArgumentException("Redis port must be between 1 and 65535 when Redis is enabled");
            }
            if (password == null) {
                throw new IllegalArgumentException("Redis password cannot be null when Redis is enabled");
            }
        }
    }
}
