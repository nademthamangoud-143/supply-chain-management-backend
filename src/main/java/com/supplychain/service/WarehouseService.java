package com.supplychain.service;

import com.supplychain.entity.Warehouse;
import com.supplychain.repository.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    public Warehouse saveWarehouse(Warehouse warehouse) {
        // If active is not provided, set it to true
        if (warehouse.getActive() == null) {
            warehouse.setActive(true);
        }

        return warehouseRepository.save(warehouse);
    }

    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    public Optional<Warehouse> getWarehouseById(Long id) {
        return warehouseRepository.findById(id);
    }

    public boolean existsByName(String name) {
        return warehouseRepository.existsByName(name);
    }

    public void deleteWarehouse(Long id) {
        warehouseRepository.deleteById(id);
    }
}