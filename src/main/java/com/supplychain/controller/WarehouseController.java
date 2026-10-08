package com.supplychain.controller;

import com.supplychain.entity.Warehouse;
import com.supplychain.service.WarehouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {
    private final WarehouseService service;
    public WarehouseController(WarehouseService service) { this.service=service; }
    @PostMapping public ResponseEntity<Warehouse> create(@RequestBody Warehouse value){if(value.getName()!=null&&service.existsByName(value.getName()))return ResponseEntity.badRequest().build();return ResponseEntity.ok(service.saveWarehouse(value));}
    @GetMapping public ResponseEntity<List<Warehouse>> all(){return ResponseEntity.ok(service.getAllWarehouses());}
    @GetMapping("/{id}") public ResponseEntity<Warehouse> get(@PathVariable Long id){return service.getWarehouseById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());}
    @PutMapping("/{id}") public ResponseEntity<Warehouse> update(@PathVariable Long id,@RequestBody Warehouse value){return service.getWarehouseById(id).map(e->{value.setId(id);return ResponseEntity.ok(service.saveWarehouse(value));}).orElse(ResponseEntity.notFound().build());}
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){if(service.getWarehouseById(id).isEmpty())return ResponseEntity.notFound().build();service.deleteWarehouse(id);return ResponseEntity.noContent().build();}
}
