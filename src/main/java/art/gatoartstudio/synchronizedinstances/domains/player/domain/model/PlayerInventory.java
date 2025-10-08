package art.gatoartstudio.synchronizedinstances.domains.player.domain.model;

import java.util.Optional;

public record PlayerInventory(
        Optional<String> inventory,
        Optional<String> armor,
        Optional<String> enderChest
) {
}
