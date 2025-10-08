package art.gatoartstudio.synchronizedinstances.domains.player.infrastructure.persistence.repositories;

import art.gatoartstudio.synchronizedinstances.domains.player.domain.aggregate.PlayerSnapshot;
import art.gatoartstudio.synchronizedinstances.domains.player.domain.repository.PlayerRepository;
import art.gatoartstudio.synchronizedinstances.domains.player.infrastructure.persistence.dto.PlayerSnapshotDto;
import art.gatoartstudio.synchronizedinstances.domains.player.infrastructure.persistence.exceptions.MongoRepositoryException;
import art.gatoartstudio.synchronizedinstances.domains.player.infrastructure.persistence.mapper.PlayerSnapshotMapper;
import art.gatoartstudio.synchronizedinstances.helpers.Log;
import art.gatoartstudio.synchronizedinstances.models.mongo.ConfigMongoModel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.MongoTimeoutException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * MongoDB implementation of PlayerRepository with both synchronous and asynchronous operations.
 * Uses a connection pool and thread pool to avoid blocking the main thread.
 */
public class MongoDbRepository implements PlayerRepository {
    private static final String COLLECTION_INDEX_FIELD = "uuid";
    private static final int THREAD_POOL_SIZE = 4;
    private static final int CONNECTION_TIMEOUT_MS = 10000; // 10 seconds
    private static final int SOCKET_TIMEOUT_MS = 5000; // 5 seconds

    private final ConfigMongoModel configMongoModel;
    private final ObjectMapper objectMapper;
    private final ExecutorService executorService;
    private final MongoClient mongoClient;
    private final MongoDatabase database;
    private final MongoCollection<Document> collection;

    public MongoDbRepository(ConfigMongoModel configMongoModel) {
        this.configMongoModel = configMongoModel;
        this.objectMapper = createObjectMapper();
        this.executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE, r -> {
            Thread thread = new Thread(r, "MongoDb-Repository-Thread");
            thread.setDaemon(true);
            return thread;
        });

        try {
            // Initialize MongoDB client with connection settings
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new com.mongodb.ConnectionString(configMongoModel.uri()))
                    .applyToConnectionPoolSettings(builder ->
                            builder.maxSize(10)
                                    .minSize(2)
                                    .maxWaitTime(CONNECTION_TIMEOUT_MS, TimeUnit.MILLISECONDS)
                                    .maxConnectionIdleTime(60000, TimeUnit.MILLISECONDS)
                    )
                    .applyToSocketSettings(builder ->
                            builder.connectTimeout(CONNECTION_TIMEOUT_MS, TimeUnit.MILLISECONDS)
                                    .readTimeout(SOCKET_TIMEOUT_MS, TimeUnit.MILLISECONDS)
                    )
                    .build();

            this.mongoClient = MongoClients.create(settings);
            this.database = mongoClient.getDatabase(configMongoModel.database());
            this.collection = database.getCollection(configMongoModel.collection());

            // Create index for UUID field for faster queries
            try {
                collection.createIndex(new Document(COLLECTION_INDEX_FIELD, 1));
                Log.info("MongoDB index created for field: " + COLLECTION_INDEX_FIELD);
            } catch (MongoException e) {
                Log.warning("Could not create MongoDB index (might already exist): ");
                Log.error(e);
            }

            Log.info("MongoDB repository initialized successfully");

        } catch (Exception e) {
            Log.error("Failed to initialize MongoDB repository");
            Log.error(e);
            throw new MongoRepositoryException.ConnectionException("Failed to initialize MongoDB connection", e);
        }
    }

    private ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    @Override
    public Optional<PlayerSnapshot> findById(UUID uuid) {
        try {
            Log.info("Finding player by UUID: " + uuid);
            return findByIdSync(uuid);
        } catch (Exception e) {
            Log.error("Error finding player by UUID: " + uuid);
            Log.error(e);
            throw new MongoRepositoryException.QueryException("Failed to find player by UUID: " + uuid, e);
        }
    }

    @Override
    public void save(PlayerSnapshot playerSnapshot) {
        try {
            Log.info("Saving player: " + playerSnapshot.getProfile().uuid());
            saveSync(playerSnapshot);
        } catch (Exception e) {
            Log.error("Error saving player: " + playerSnapshot.getProfile().uuid());
            Log.error(e);
            throw new MongoRepositoryException.QueryException("Failed to save player", e);
        }
    }

    @Override
    public void delete(UUID uuid) {
        try {
            Log.info("Deleting player by UUID: " + uuid);
            deleteSync(uuid);
        } catch (Exception e) {
            Log.error("Error deleting player by UUID: " + uuid);
            Log.error(e);
            throw new MongoRepositoryException.QueryException("Failed to delete player by UUID: " + uuid, e);
        }
    }

    /**
     * Asynchronous version of findById that runs on a separate thread pool.
     * This method prevents blocking the main server thread.
     */
    public CompletableFuture<Optional<PlayerSnapshot>> findByIdAsync(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Log.info("Async finding player by UUID: " + uuid);
                return findByIdSync(uuid);
            } catch (Exception e) {
                Log.error("Error in async find by UUID: " + uuid);
                Log.error(e);
                throw new MongoRepositoryException.QueryException("Failed to find player by UUID: " + uuid, e);
            }
        }, executorService);
    }

    /**
     * Asynchronous version of save that runs on a separate thread pool.
     * This method prevents blocking the main server thread.
     */
    public CompletableFuture<Void> saveAsync(PlayerSnapshot playerSnapshot) {
        return CompletableFuture.runAsync(() -> {
            try {
                Log.info("Async saving player: " + playerSnapshot.getProfile().uuid());
                saveSync(playerSnapshot);
            } catch (Exception e) {
                Log.error("Error in async save for player: " + playerSnapshot.getProfile().uuid());
                Log.error(e);
                throw new MongoRepositoryException.QueryException("Failed to save player", e);
            }
        }, executorService);
    }

    /**
     * Asynchronous version of delete that runs on a separate thread pool.
     * This method prevents blocking the main server thread.
     */
    public CompletableFuture<Void> deleteAsync(UUID uuid) {
        return CompletableFuture.runAsync(() -> {
            try {
                Log.info("Async deleting player by UUID: " + uuid);
                deleteSync(uuid);
            } catch (Exception e) {
                Log.error("Error in async delete by UUID: " + uuid);
                Log.error(e);
                throw new MongoRepositoryException.QueryException("Failed to delete player by UUID: " + uuid, e);
            }
        }, executorService);
    }

    /**
     * Synchronous implementation of findById.
     */
    private Optional<PlayerSnapshot> findByIdSync(UUID uuid) {
        try {
            Document query = new Document(COLLECTION_INDEX_FIELD, uuid.toString());
            Document document = collection.find(query).first();

            if (document == null) {
                Log.warning("Player not found for UUID: " + uuid);
                return Optional.empty();
            }

            PlayerSnapshotDto dto = deserializeDocument(document);
            PlayerSnapshot playerSnapshot = PlayerSnapshotMapper.fromDto(dto);

            Log.info("Player found and deserialized for UUID: " + uuid);
            return Optional.of(playerSnapshot);

        } catch (MongoTimeoutException e) {
            throw new MongoRepositoryException.TimeoutException("Timeout while finding player by UUID: " + uuid, e);
        } catch (MongoException e) {
            throw new MongoRepositoryException.QueryException("MongoDB error while finding player by UUID: " + uuid, e);
        } catch (Exception e) {
            throw new MongoRepositoryException.SerializationException("Error deserializing player data for UUID: " + uuid, e);
        }
    }

    /**
     * Synchronous implementation of save.
     */
    private void saveSync(PlayerSnapshot playerSnapshot) {
        try {
            PlayerSnapshotDto dto = PlayerSnapshotMapper.toDto(playerSnapshot);
            dto.updateTimestamp(); // Update the timestamp

            Document document = serializeToDocument(dto);
            Document query = new Document(COLLECTION_INDEX_FIELD, playerSnapshot.getProfile().uuid().toString());

            // Use upsert to either update existing document or insert new one
            ReplaceOptions options = new ReplaceOptions().upsert(true);
            collection.replaceOne(query, document, options);

            Log.info("Player saved successfully for UUID: " + playerSnapshot.getProfile().uuid());

        } catch (MongoTimeoutException e) {
            throw new MongoRepositoryException.TimeoutException("Timeout while saving player: " + playerSnapshot.getProfile().uuid(), e);
        } catch (MongoException e) {
            throw new MongoRepositoryException.QueryException("MongoDB error while saving player: " + playerSnapshot.getProfile().uuid(), e);
        } catch (Exception e) {
            throw new MongoRepositoryException.SerializationException("Error serializing player data for UUID: " + playerSnapshot.getProfile().uuid(), e);
        }
    }

    /**
     * Synchronous implementation of delete.
     */
    private void deleteSync(UUID uuid) {
        try {
            Document query = new Document(COLLECTION_INDEX_FIELD, uuid.toString());
            var result = collection.deleteOne(query);

            if (result.getDeletedCount() > 0) {
                Log.info("Player deleted successfully for UUID: " + uuid);
            } else {
                Log.warning("No player found to delete for UUID: " + uuid);
            }

        } catch (MongoTimeoutException e) {
            throw new MongoRepositoryException.TimeoutException("Timeout while deleting player by UUID: " + uuid, e);
        } catch (MongoException e) {
            throw new MongoRepositoryException.QueryException("MongoDB error while deleting player by UUID: " + uuid, e);
        }
    }

    /**
     * Converts a PlayerSnapshotDto to a MongoDB Document.
     */
    private Document serializeToDocument(PlayerSnapshotDto dto) {
        try {
            String json = objectMapper.writeValueAsString(dto);
            return Document.parse(json);
        } catch (Exception e) {
            throw new MongoRepositoryException.SerializationException("Error serializing PlayerSnapshotDto to Document", e);
        }
    }

    /**
     * Converts a MongoDB Document to a PlayerSnapshotDto.
     */
    private PlayerSnapshotDto deserializeDocument(Document document) {
        try {
            String json = document.toJson();
            return objectMapper.readValue(json, PlayerSnapshotDto.class);
        } catch (Exception e) {
            throw new MongoRepositoryException.SerializationException("Error deserializing Document to PlayerSnapshotDto", e);
        }
    }

    /**
     * Utility method to run async operations and then execute a callback on the main thread.
     * This is useful for Bukkit/Spigot plugins that need to update the main thread after async operations.
     */
    public <T> void runAsyncWithCallback(CompletableFuture<T> asyncOperation, 
                                        java.util.function.Consumer<T> onSuccess,
                                        java.util.function.Consumer<Throwable> onError) {
        asyncOperation.whenComplete((result, throwable) -> {
            // Schedule the callback to run on the main thread
            Plugin plugin = getPlugin();
            if (plugin != null && plugin.isEnabled()) {
                Bukkit.getScheduler().runTask(plugin, () -> {
                    if (throwable != null) {
                        onError.accept(throwable);
                    } else {
                        onSuccess.accept(result);
                    }
                });
            } else {
                // Fallback: run directly if plugin is not available
                if (throwable != null) {
                    onError.accept(throwable);
                } else {
                    onSuccess.accept(result);
                }
            }
        });
    }

    /**
     * Gets the plugin instance for scheduling tasks on the main thread.
     * This assumes there's a way to get the plugin instance.
     */
    private Plugin getPlugin() {
        try {
            return Bukkit.getPluginManager().getPlugin("SynchronizedInstances");
        } catch (Exception e) {
            Log.warning("Could not get plugin instance for main thread scheduling");
            return null;
        }
    }

    /**
     * Closes the MongoDB connection and shuts down the thread pool.
     * This should be called when the plugin is disabled.
     */
    public void close() {
        Log.info("Shutting down MongoDB repository");
        
        try {
            if (executorService != null && !executorService.isShutdown()) {
                executorService.shutdown();
                if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
                    Log.warning("Executor service did not terminate gracefully, forcing shutdown");
                    executorService.shutdownNow();
                }
            }
        } catch (InterruptedException e) {
            Log.warning("Interrupted while shutting down executor service");
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }

        try {
            if (mongoClient != null) {
                mongoClient.close();
                Log.info("MongoDB client closed successfully");
            }
        } catch (Exception e) {
            Log.error("Error closing MongoDB client");
            Log.error(e);
        }
    }

    /**
     * Checks if the repository is healthy by attempting a simple ping to the database.
     */
    public boolean isHealthy() {
        try {
            database.runCommand(new Document("ping", 1));
            return true;
        } catch (Exception e) {
            Log.error("MongoDB health check failed");
            Log.error(e);
            return false;
        }
    }
}
