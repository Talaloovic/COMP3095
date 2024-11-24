package ca.gbc.inventoryservice.service;

public interface InventoryService {

    public boolean inStock(String skuCode, Integer quantity);
}
