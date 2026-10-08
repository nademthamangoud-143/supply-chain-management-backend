package com.supplychain.controller;

import com.supplychain.entity.Order;
import com.supplychain.entity.Warehouse;
import com.supplychain.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    // Create order
    @PostMapping
    public ResponseEntity<Order> create(@RequestBody Order value) {

        if (value.getOrderNumber() != null
                && service.existsByOrderNumber(value.getOrderNumber())) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(service.saveOrder(value));
    }

    // Get all orders
    @GetMapping
    public ResponseEntity<List<Order>> all() {
        return ResponseEntity.ok(service.getAllOrders());
    }

    // Get order by ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> get(@PathVariable Long id) {
        return service.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get order by order number
    @GetMapping("/number/{number}")
    public ResponseEntity<Order> byNumber(@PathVariable String number) {
        return service.findByOrderNumber(number)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get orders by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> byStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                service.findByStatus(status)
        );
    }

    // Get orders by warehouse
    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<Order>> byWarehouse(
            @PathVariable Long warehouseId) {

        Warehouse warehouse = new Warehouse();
        warehouse.setId(warehouseId);

        return ResponseEntity.ok(
                service.findByWarehouse(warehouse)
        );
    }

    // Update order
    @PutMapping("/{id}")
    public ResponseEntity<Order> update(
            @PathVariable Long id,
            @RequestBody Order value) {

        return service.getOrderById(id)
                .map(existingOrder -> {
                    value.setId(id);
                    return ResponseEntity.ok(
                            service.saveOrder(value)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete order
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        if (service.getOrderById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        service.deleteOrder(id);

        return ResponseEntity.noContent().build();
    }
}