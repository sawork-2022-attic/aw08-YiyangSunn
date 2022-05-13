package com.micropos.carts.repository;

import com.micropos.api.dto.ProductDto;
import com.micropos.carts.model.Item;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class SimpleCartRepository implements CartRepository {

    // 防止有线程问题
    private final Map<String, Item> cartTable = new ConcurrentHashMap<>();

    @Override
    public List<Item> allItems() {
        List<Item> itemList = new ArrayList<>(cartTable.values());
        // 按加入购物车的时间进行排序
        itemList.sort((item1, item2) -> {
            long tsDiff = item1.getTimeStamp() - item2.getTimeStamp();
            return tsDiff < 0 ? -1 : tsDiff > 0 ? 1 : 0;
        });
        return itemList;
    }

    @Override
    public Item findItem(String productId) {
        return cartTable.getOrDefault(productId, null);
    }

    @Override
    public boolean deleteItem(String productId) {
        if (cartTable.containsKey(productId)) {
            cartTable.remove(productId);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteAll() {
        cartTable.clear();
        return true;
    }

    @Override
    public boolean updateItem(String productId, int newQuantity) {
        if (cartTable.containsKey(productId)) {
            Item item = cartTable.get(productId);
            // 更新数量值
            item.setQuantity(newQuantity);
            return true;
        }
        return false;
    }

    @Override
    public boolean insertItem(ProductDto productDto, int quantity) {
        // 添加数量必须为正
        if (quantity <= 0) {
            return false;
        }
        // 购物车中应该没有该商品
        if (cartTable.containsKey(productDto.getId())) {
            return false;
        }
        cartTable.put(productDto.getId(), new Item(productDto, quantity, System.currentTimeMillis()));
        return true;
    }

}
