package com.supplychain.controller;

import com.supplychain.entity.Product;
import com.supplychain.entity.StockMovement;
import com.supplychain.entity.Warehouse;
import com.supplychain.service.StockMovementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock-movements")
public class StockMovementController {

    private final StockMovementService service;

    public StockMovementController(StockMovementService service) {
        this.service = service;
    }

    // Create stock movement
    @PostMapping
    public ResponseEntity<StockMovement> create(
            @RequestBody StockMovement value) {

        return ResponseEntity.ok(
                service.saveMovement(value)
        );
    }

    // Get all stock movements
    @GetMapping
    public ResponseEntity<List<StockMovement>> all() {

        return ResponseEntity.ok(
                service.getAllMovements()
        );
    }

    // Get stock movements by product
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<StockMovement>> byProduct(
            @PathVariable Long productId) {

        Product product = new Product();
        product.setId(productId);

        return ResponseEntity.ok(
                service.findByProduct(product)
        );
    }

    // Get stock movements by warehouse
    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<StockMovement>> byWarehouse(
            @PathVariable Long warehouseId) {

        Warehouse warehouse = new Warehouse();
        warehouse.setId(warehouseId);

        return ResponseEntity.ok(
                service.findByWarehouse(warehouse)
        );
    }

    // Get stock movements by product and warehouse
    @GetMapping("/product/{productId}/warehouse/{warehouseId}")
    public ResponseEntity<List<StockMovement>> byBoth(
            @PathVariable Long productId,
            @PathVariable Long warehouseId) {

        Product product = new Product();
        product.setId(productId);

        Warehouse warehouse = new Warehouse();
        warehouse.setId(warehouseId);

        return ResponseEntity.ok(
                service.findByProductAndWarehouse(product, warehouse)
        );
    }
}