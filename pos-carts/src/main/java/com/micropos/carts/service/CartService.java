package com.micropos.carts.service;

import com.micropos.carts.model.Item;

import java.util.List;

public interface CartService {

    List<Item> items();

    Item getItem(String productId);

    boolean removeAll();

    boolean removeItem(String productId);

    boolean updateItem(String productId, int quantity);

    String checkout();
}
