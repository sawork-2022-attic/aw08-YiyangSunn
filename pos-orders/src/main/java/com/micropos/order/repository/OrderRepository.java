package com.micropos.order.repository;

import com.micropos.order.model.Order;

import java.util.List;

public interface OrderRepository {

    List<Order> findOrders();

    Order findOrderById(String orderId);

    boolean saveOrder(Order order);
}
