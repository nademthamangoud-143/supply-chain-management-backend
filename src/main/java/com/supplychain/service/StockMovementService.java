package com.supplychain.service;

import com.supplychain.entity.Product;
import com.supplychain.entity.StockMovement;
import com.supplychain.entity.Warehouse;
import com.supplychain.repository.StockMovementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockMovementService {

    private final StockMovementRepository stockMovementRepository;

    public StockMovementService(
            StockMovementRepository stockMovementRepository) {

        this.stockMovementRepository = stockMovementRepository;
    }

    public StockMovement saveMovement(StockMovement movement) {
        return stockMovementRepository.save(movement);
    }

    public List<StockMovement> getAllMovements() {
        return stockMovementRepository.findAll();
    }

    public List<StockMovement> findByProduct(Product product) {
        return stockMovementRepository.findByProduct(product);
    }

    public List<StockMovement> findByWarehouse(Warehouse warehouse) {
        return stockMovementRepository.findByWarehouse(warehouse);
    }

    public List<StockMovement> findByProductAndWarehouse(
            Product product,
            Warehouse warehouse) {

        return stockMovementRepository.findByProductAndWarehouse(
                product,
                warehouse
        );
    }
}