package com.supplychain.service;

import com.supplychain.entity.Supplier;
import com.supplychain.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public Supplier saveSupplier(Supplier supplier) {
        // If active is not provided, set it to true
        if (supplier.getActive() == null) {
            supplier.setActive(true);
        }

        return supplierRepository.save(supplier);
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Optional<Supplier> getSupplierById(Long id) {
        return supplierRepository.findById(id);
    }

    public boolean existsByEmail(String email) {
        return supplierRepository.existsByEmail(email);
    }

    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
    }
}