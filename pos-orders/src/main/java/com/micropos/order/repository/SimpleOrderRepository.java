package com.micropos.order.repository;

import com.micropos.order.model.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class SimpleOrderRepository implements OrderRepository {

    private final Map<String, Order> orders = new ConcurrentHashMap<>();

    @Override
    public List<Order> findOrders() {
        return new ArrayList<>(orders.values());
    }

    @Override
    public Order findOrderById(String orderId) {
        return orders.getOrDefault(orderId, null);
    }

    @Override
    public boolean saveOrder(Order order) {
        orders.put(order.getOrderId(), order);
        return true;
    }
}
