package art.gatoartstudio.synchronizedinstances.models.mongo;

public record ConfigMongoModel(String uri, String database, String collection) {
    public ConfigMongoModel {
        if (uri == null || uri.isEmpty()) {
            throw new IllegalArgumentException("MongoDB URI cannot be null or empty");
        }
        if (database == null || database.isEmpty()) {
            throw new IllegalArgumentException("Database name cannot be null or empty");
        }
        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException("Collection name cannot be null or empty");
        }
    }
}
