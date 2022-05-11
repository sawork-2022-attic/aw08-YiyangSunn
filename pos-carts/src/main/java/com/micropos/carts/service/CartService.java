package com.micropos.carts.service;

import com.micropos.carts.model.Item;

import java.util.List;

public interface CartService {

    public List<Item> items();

    public Item getItem(String productId);

    public boolean removeAll();

    public boolean removeItem(String productId);

    public boolean updateItem(String productId, int quantity);

}
