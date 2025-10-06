# SynchronizedInstances Plugin

A high-performance **Paper 1.21.x plugin** designed to synchronize player data across multiple Minecraft servers, with optional **Redis-powered cross-server chat**.  
The project follows **Hexagonal Architecture** to keep the core domain logic independent from infrastructure concerns, making it scalable, maintainable, and future-proof.

---

## 🚀 Features

- **Player Data Synchronization** across multiple servers:
  - Profile: `uuid`, `name`, `firstJoin`, `lastSeen`.
  - State: `gamemode`, `health`, `food`, `saturation`, `exp`, `level`, `fireTicks`, `potionEffects`.
  - Locations: `lastLocation`, `bedSpawn`.
  - Inventory: player inventory, armor, enderChest (safe Base64/JSON serialization).
  - Statistics: `playTime`, `deaths`, `kills`, basic stats.

- **Events handled and persisted**:
  - `PlayerJoinEvent`, `PlayerQuitEvent`, `PlayerDeathEvent`, `PlayerRespawnEvent`.
  - Inventory and XP changes (periodically or on quit).
  - Teleports (update `lastLocation`).

- **Consistency across servers**:
  - Snapshot load on join.
  - In-memory cache during session.
  - Background diffs written asynchronously.
  - Flush on quit.

- **Cross-Server Chat (optional)**:
  - Powered by **Redis Pub/Sub**.
  - Global channel: `network:chat:global`.
  - Rate-limiting and antiflood protections.
  - Basic input validation.

- **Technical stack**:
  - Java 21
  - Maven
  - MongoDB (official driver)
  - Redis (Jedis or Lettuce)
  - Docker Compose (for local development and testing)
  - GitHub Actions (CI/CD)

---

## Project Structure

The project follows a **hexagonal architecture**. Entry points and infrastructure are separated from domain logic.

```

src/main/java/art/gatoartstudio/synchronizedInstances/
│
├── SynchronizedInstances.java # MAIN class — root package
├── listeners/ # Bukkit/Paper listeners (root plugin package)
│ ├── PlayerJoinListener.java
│ ├── PlayerQuitListener.java
│ └── ...
├── commands/ # Optional command executors (root)
│ └── ...
├── helpers/ # Utils/helpers (root)
│ ├── AsyncExecutor.java
│ └── SerializationUtils.java
│
├── domains/ # Domain containers (one folder per domain)
│ └── server/ # <--- Server domain (all layers inside)
│ ├── domain/ # Pure domain model + interfaces
│ │ ├── model/
│ │ │ ├── PlayerSnapshot.java
│ │ │ ├── PlayerProfile.java
│ │ │ └── ...
│ │ ├── repository/
│ │ │ └── PlayerRepository.java # interface (returns CompletableFuture / async)
│ │ └── event/
│ │ └── PlayerEvents.java
│ │
│ ├── application/ # Use-cases / orchestration (depends on domain interfaces)
│ │ ├── PlayerSyncService.java
│ │ ├── SyncScheduler.java
│ │ └── PlayerChatService.java # if chat logic sits here
│ │
│ └── infrastructure/ # Adapters implementing domain interfaces
│ ├── mongo/
│ │ └── MongoPlayerRepository.java
│ ├── redis/
│ │ └── RedisChatAdapter.java
│ └── serialization/
│ ├── InventorySerializer.java
│ └── PotionEffectSerializer.java
│
└── config/
└── PluginConfig.java

```

---

## 🧩 Development Setup

### Requirements
- Java 21+
- Maven
- Docker & Docker Compose (for local testing)

### Local Development
1. Clone the repository:
```bash
git clone https://github.com/GatoArtStudio/SynchronizedInstances.git
cd SynchronizedInstances
```

2. Build the plugin:

```bash
mvn package
```

3. The plugin JAR will be located in:

```
target/synchronized-instances-<version>.jar
```

4. For local multi-server testing:

```bash
docker-compose up -d
```

This spins up:

* MongoDB (`localhost:27017`)
* Redis (`localhost:6379`)
* Two Paper servers (`localhost:25565`, `localhost:25566`)

---

## ⚡ CI/CD

The repository includes a **GitHub Actions workflow**:

* **Build & Test** on each push and pull request.
* **Release pipeline** that builds and uploads JARs to GitHub Releases when a tag is created.

---

## 📜 License

This project is licensed under the MIT License.
See the [LICENSE](https://github.com/GatoArtStudio/SynchronizedInstances/blob/main/LICENSE) file for details.