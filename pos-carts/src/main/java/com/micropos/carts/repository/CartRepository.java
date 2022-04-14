package com.micropos.carts.repository;

import com.micropos.api.dto.ProductDto;
import com.micropos.carts.model.Item;

import java.util.List;

public interface CartRepository {

    public List<Item> allItems();

    public Item findItem(String productId);

    // 成功返回 true，失败返回 false

    public boolean deleteItem(String productId);

    public boolean deleteAll();

    public boolean updateItem(String productId, int newQuantity);

    public boolean insertItem(ProductDto productDto, int quantity);

}
