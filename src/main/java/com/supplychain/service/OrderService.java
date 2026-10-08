package com.supplychain.service;

import com.supplychain.entity.Order;
import com.supplychain.entity.Warehouse;
import com.supplychain.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order saveOrder(Order order) {
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