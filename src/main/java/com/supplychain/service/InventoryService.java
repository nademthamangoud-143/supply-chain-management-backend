package com.supplychain.service;

import com.supplychain.entity.Inventory;
import com.supplychain.entity.Product;
import com.supplychain.entity.Warehouse;
import com.supplychain.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public Inventory saveInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public Optional<Inventory> getInventoryById(Long id) {
        return inventoryRepository.findById(id);
    }

    public Optional<Inventory> findByProductAndWarehouse(
            Product product,
            Warehouse warehouse) {

        return inventoryRepository.findByProductAndWarehouse(
                product,
                warehouse
        );
    }

    public List<Inventory> findByWarehouse(Warehouse warehouse) {
        return inventoryRepository.findByWarehouse(warehouse);
    }

    public List<Inventory> findByProduct(Product product) {
        return inventoryRepository.findByProduct(product);
    }

    public void deleteInventory(Long id) {
        inventoryRepository.deleteById(id);
    }
}