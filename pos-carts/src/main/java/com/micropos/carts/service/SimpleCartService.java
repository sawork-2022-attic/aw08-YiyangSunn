package com.micropos.carts.service;

import com.micropos.api.dto.ItemDto;
import com.micropos.api.dto.PaymentDto;
import com.micropos.api.dto.ProductDto;
import com.micropos.carts.mapper.ItemMapper;
import com.micropos.carts.model.Item;
import com.micropos.carts.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
public class SimpleCartService implements CartService {

    private static final String POS_PRODUCTS_URL = "http://pos-products/api";

    private static final String POS_COUNTER_URL = "http://pos-counter/api";

    private static final String POS_ORDER_URL = "http://pos-orders/api";

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ItemMapper itemMapper;

    @Override
    @Cacheable(value = "items", unless = "#result != null && #result.size() > 0")
    public List<Item> items() {
        return cartRepository.allItems();
    }

    @Override
    @Cacheable(value = "getItem", unless = "#result != null")
    public Item getItem(String productId) {
        return cartRepository.findItem(productId);
    }

    // 保持缓存一致性
    @Override
    @CacheEvict(value = {"items", "getItem"}, allEntries = true)
    public boolean removeAll() {
        return cartRepository.deleteAll();
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "items", allEntries = true),
            @CacheEvict(value = "getItem", key = "#root.args[0]")
    })
    public boolean removeItem(String productId) {
        return cartRepository.deleteItem(productId);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "items", allEntries = true),
            @CacheEvict(value = "getItem", key = "#root.args[0]")
    })
    public boolean updateItem(String productId, int quantity) {
        Item item = cartRepository.findItem(productId);
        if (item == null) {
            if (quantity <= 0) {
                // 新加入的数量必须为正
                return false;
            }
            // 加入一件新商品，先从 pos-products 取数据
            ProductDto productDto = restTemplate.getForObject(POS_PRODUCTS_URL + "/products/" + productId, ProductDto.class);
            if (productDto == null) {
                // 没有此商品
                return false;
            }
            // 加入购物车
            return cartRepository.insertItem(productDto, quantity);
        }
        // 购物车中已有此商品，检查剩余数量
        int newQuantity = item.getQuantity() + quantity;
        if (newQuantity < 0) {
            return false;
        } else if (newQuantity == 0) {
            // 删除该物品
            return cartRepository.deleteItem(productId);
        }
        // 更新数量
        return cartRepository.updateItem(productId, newQuantity);
    }

    @Override
    public String checkout() {
        List<Item> items = cartRepository.allItems();
        // 购物车是空的
        if (items.size() == 0) {
            return null;
        }
        // 计算总价
        Double total = restTemplate.postForObject(
                POS_COUNTER_URL + "/counter/checkout",
                itemMapper.toItemDtos(cartRepository.allItems()),
                Double.class
        );
        if (total == null) {
            log.error("Counter service return null value");
        }
        // 生成订单
        List<ItemDto> itemDtoList = itemMapper.toItemDtos(items);
        PaymentDto paymentDto = new PaymentDto().total(total).items(itemDtoList);
        return restTemplate.postForObject(POS_ORDER_URL + "/orders", paymentDto, String.class);
    }
}
