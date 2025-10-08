package art.gatoartstudio.synchronizedinstances.domains.player.infrastructure.persistence.exceptions;

/**
 * Custom exception for MongoDB repository operations.
 * This exception wraps MongoDB-specific errors to provide cleaner error handling.
 */
public class MongoRepositoryException extends RuntimeException {

    public MongoRepositoryException(String message) {
        super(message);
    }

    public MongoRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public MongoRepositoryException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception for connection-related issues.
     */
    public static class ConnectionException extends MongoRepositoryException {
        public ConnectionException(String message, Throwable cause) {
            super("MongoDB connection error: " + message, cause);
        }
    }

    /**
     * Exception for serialization/deserialization issues.
     */
    public static class SerializationException extends MongoRepositoryException {
        public SerializationException(String message, Throwable cause) {
            super("MongoDB serialization error: " + message, cause);
        }
    }

    /**
     * Exception for query-related issues.
     */
    public static class QueryException extends MongoRepositoryException {
        public QueryException(String message, Throwable cause) {
            super("MongoDB query error: " + message, cause);
        }
    }

    /**
     * Exception for timeout issues.
     */
    public static class TimeoutException extends MongoRepositoryException {
        public TimeoutException(String message, Throwable cause) {
            super("MongoDB timeout error: " + message, cause);
        }
    }
}