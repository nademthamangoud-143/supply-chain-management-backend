package com.supplychain.controller;

import com.supplychain.entity.Inventory;
import com.supplychain.entity.Product;
import com.supplychain.entity.Warehouse;
import com.supplychain.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }

    // Create inventory
    @PostMapping
    public ResponseEntity<Inventory> create(@RequestBody Inventory value) {
        return ResponseEntity.ok(service.saveInventory(value));
    }

    // Get all inventory
    @GetMapping
    public ResponseEntity<List<Inventory>> all() {
        return ResponseEntity.ok(service.getAllInventory());
    }

    // Get inventory by ID
    @GetMapping("/{id}")
    public ResponseEntity<Inventory> get(@PathVariable Long id) {
        return service.getInventoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get inventory by warehouse
    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<Inventory>> byWarehouse(
            @PathVariable Long warehouseId) {

        Warehouse warehouse = new Warehouse();
        warehouse.setId(warehouseId);

        return ResponseEntity.ok(
                service.findByWarehouse(warehouse)
        );
    }

    // Get inventory by product
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Inventory>> byProduct(
            @PathVariable Long productId) {

        Product product = new Product();
        product.setId(productId);

        return ResponseEntity.ok(
                service.findByProduct(product)
        );
    }

    // Update inventory
    @PutMapping("/{id}")
    public ResponseEntity<Inventory> update(
            @PathVariable Long id,
            @RequestBody Inventory value) {

        return service.getInventoryById(id)
                .map(existingInventory -> {
                    value.setId(id);
                    return ResponseEntity.ok(
                            service.saveInventory(value)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete inventory
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        if (service.getInventoryById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        service.deleteInventory(id);

        return ResponseEntity.noContent().build();
    }
}