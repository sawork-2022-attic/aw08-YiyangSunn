package com.micropos.order.service;

import com.micropos.order.model.Order;
import com.micropos.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleOrderService implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> orders() {
        return orderRepository.findOrders();
    }

    @Override
    public Order orderById(String orderId) {
        return orderRepository.findOrderById(orderId);
    }

    @Override
    public boolean createOrder() {
        return false;
    }
}
