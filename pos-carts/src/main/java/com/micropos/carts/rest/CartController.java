package com.micropos.carts.rest;

import com.micropos.api.controller.CartsApi;
import com.micropos.api.dto.ItemDto;
import com.micropos.carts.mapper.ItemMapper;
import com.micropos.carts.model.Item;
import com.micropos.carts.service.CartService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

// 和 ProductController 基本一样
@Controller
@RequestMapping("/api")
public class CartController implements CartsApi {

    private final CartService cartService;

    private final ItemMapper itemMapper;

    public CartController(CartService cartService, ItemMapper itemMapper) {
        this.cartService = cartService;
        this.itemMapper = itemMapper;
    }

    @Override
    @CircuitBreaker(name = "cart-controller", fallbackMethod = "defaultFallback")
    public ResponseEntity<Void> emptyItems() {
        if (cartService.removeAll()) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @Override
    @CircuitBreaker(name = "cart-controller", fallbackMethod = "listItemsFallback")
    public ResponseEntity<List<ItemDto>> listItems() {
        List<ItemDto> itemDtos = itemMapper.toItemDtos(cartService.items());
        return ResponseEntity.ok(itemDtos);
    }

    @Override
    @CircuitBreaker(name = "cart-controller", fallbackMethod = "defaultFallback")
    public ResponseEntity<Void> removeItemById(String productId) {
        if (cartService.removeItem(productId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @Override
    @CircuitBreaker(name = "cart-controller", fallbackMethod = "showItemByIdFallback")
    public ResponseEntity<ItemDto> showItemById(String productId) {
        Item item = cartService.getItem(productId);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(itemMapper.toItemDto(item));
    }

    @Override
    @CircuitBreaker(name = "cart-controller", fallbackMethod = "defaultFallback")
    public ResponseEntity<Void> updateItemById(String productId, Integer quantity) {
        if (cartService.updateItem(productId, quantity)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @Override
    @CircuitBreaker(name = "cart-controller", fallbackMethod = "checkoutAllFallback")
    public ResponseEntity<String> checkoutAll() {
        String orderId = cartService.checkout();
        if (orderId != null) {
            return ResponseEntity.ok().body(orderId);
        }
        return ResponseEntity.badRequest().build();
    }

    // 默认的降级处理函数
    public ResponseEntity<Void> defaultFallback(Throwable throwable) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

    public ResponseEntity<List<ItemDto>> listItemsFallback(Throwable throwable) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

    public ResponseEntity<ItemDto> showItemByIdFallback(Throwable throwable) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

    public ResponseEntity<String> checkoutAllFallback(Throwable throwable) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }
}
