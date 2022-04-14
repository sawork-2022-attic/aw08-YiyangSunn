package com.micropos.products.rest;

import com.micropos.api.controller.ProductsApi;
import com.micropos.api.dto.ProductDto;
import com.micropos.products.mapper.ProductMapper;
import com.micropos.products.model.Product;
import com.micropos.products.service.ProductService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api")
public class ProductController implements ProductsApi {

    private final ProductMapper productMapper;

    private final ProductService productService;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productMapper = productMapper;
        this.productService = productService;
    }

    @Override
    @CircuitBreaker(name = "product-controller", fallbackMethod = "listProductsFallback")
    public ResponseEntity<List<ProductDto>> listProducts() {
        List<ProductDto> products = new ArrayList<>(productMapper.toProductsDto(this.productService.products()));
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<ProductDto>>(products, HttpStatus.OK);
    }

    @Override
    @CircuitBreaker(name = "product-controller", fallbackMethod = "showProductByIdFallback")
    public ResponseEntity<ProductDto> showProductById(String productId) {
        Product product = productService.getProduct(productId);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productMapper.toProductDto(product));
    }

    // resilience4j 要求降级函数和原函数的返回值必须相同，这样就不能返回一条消息了
    // 因此可能需要统一返回一个 Result 对象，而不是直接在 ResponseEntity 中返回实际类型

    // listProducts 的降级处理
    public ResponseEntity<List<ProductDto>> listProductsFallback(Throwable throwable) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

    // showProductById 的降级处理
    public ResponseEntity<ProductDto> showProductByIdFallback(Throwable throwable) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

}
