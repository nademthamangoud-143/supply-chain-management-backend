package com.supplychain.controller;

import com.supplychain.entity.Supplier;
import com.supplychain.service.SupplierService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {
    private final SupplierService service;
    public SupplierController(SupplierService service) { this.service = service; }
    @PostMapping public ResponseEntity<Supplier> create(@RequestBody Supplier value) {
        if (value.getEmail()!=null && service.existsByEmail(value.getEmail())) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(service.saveSupplier(value));
    }
    @GetMapping public ResponseEntity<List<Supplier>> all() { return ResponseEntity.ok(service.getAllSuppliers()); }
    @GetMapping("/{id}") public ResponseEntity<Supplier> get(@PathVariable Long id) { return service.getSupplierById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); }
    @PutMapping("/{id}") public ResponseEntity<Supplier> update(@PathVariable Long id,@RequestBody Supplier value){ return service.getSupplierById(id).map(e->{value.setId(id);return ResponseEntity.ok(service.saveSupplier(value));}).orElse(ResponseEntity.notFound().build()); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){if(service.getSupplierById(id).isEmpty())return ResponseEntity.notFound().build();service.deleteSupplier(id);return ResponseEntity.noContent().build();}
}
