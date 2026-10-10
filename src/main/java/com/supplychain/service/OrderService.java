
package com.supplychain.service;

import com.supplychain.entity.Order;
import com.supplychain.entity.Warehouse;
import com.supplychain.repository.OrderRepository;
import com.supplychain.repository.WarehouseRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final WarehouseRepository warehouseRepository;

    public OrderService(
            OrderRepository orderRepository,
            WarehouseRepository warehouseRepository) {

        this.orderRepository = orderRepository;
        this.warehouseRepository = warehouseRepository;
    }

    @Transactional
    public Order saveOrder(Order order) {

        if (order.getWarehouse() == null
                || order.getWarehouse().getId() == null) {

            throw new IllegalArgumentException(
                    "Please select a warehouse for this order.");
        }

        Long warehouseId = order.getWarehouse().getId();

        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Warehouse with ID " + warehouseId
                                + " does not exist. Please select a valid warehouse."));

        order.setWarehouse(warehouse);

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Optional<Order> findByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }

    public boolean existsByOrderNumber(String orderNumber) {
        return orderRepository.existsByOrderNumber(orderNumber);
    }

    public List<Order> findByWarehouse(Warehouse warehouse) {
        return orderRepository.findByWarehouse(warehouse);
    }

    public List<Order> findByStatus(String status) {
        return orderRepository.findByStatus(status);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
