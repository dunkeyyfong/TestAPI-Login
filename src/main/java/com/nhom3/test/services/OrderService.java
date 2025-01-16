package com.nhom3.test.services;

import com.nhom3.test.entities.Order;
import com.nhom3.test.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order getOrderById(String id) {
        return orderRepository.findById(id).orElse(null);
    }

    public void deleteOrder(String id) {
        orderRepository.deleteById(id);
    }
}
