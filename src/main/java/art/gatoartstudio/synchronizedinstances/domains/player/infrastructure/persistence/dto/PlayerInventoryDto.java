package art.gatoartstudio.synchronizedinstances.domains.player.infrastructure.persistence.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerInventoryDto {
    
    @JsonProperty("inventory")
    private String inventory;
    
    @JsonProperty("armor")
    private String armor;
    
    @JsonProperty("enderChest")
    private String enderChest;

    // Default constructor for Jackson
    public PlayerInventoryDto() {}

    public PlayerInventoryDto(String inventory, String armor, String enderChest) {
        this.inventory = inventory;
        this.armor = armor;
        this.enderChest = enderChest;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getArmor() {
        return armor;
    }

    public void setArmor(String armor) {
        this.armor = armor;
    }

    public String getEnderChest() {
        return enderChest;
    }

    public void setEnderChest(String enderChest) {
        this.enderChest = enderChest;
    }
}