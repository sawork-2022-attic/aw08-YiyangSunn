package com.micropos.products.rest;

import com.micropos.api.controller.ProductsApi;
import com.micropos.api.dto.PageResultDto;
import com.micropos.api.dto.ProductDto;
import com.micropos.products.mapper.ProductMapper;
import com.micropos.products.model.PageResult;
import com.micropos.products.model.Product;
import com.micropos.products.service.ProductService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
        List<ProductDto> products = new ArrayList<>(productMapper.toProductsDto(productService.products()));
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
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

    @Override
    @CircuitBreaker(name = "product-controller", fallbackMethod = "getProductsInPageFallback")
    public ResponseEntity<PageResultDto> getProductsInPage(Integer page, Integer pageSize) {
        PageResult pageResult = productService.productsInPage(page, pageSize);
        List<ProductDto> productDtos = new ArrayList<>(productMapper.toProductsDto(pageResult.getProducts()));
        return ResponseEntity.ok(new PageResultDto().total(pageResult.getTotal()).products(productDtos));
    }

    // resilience4j 要求降级函数和原函数的返回值必须相同，这样就不能返回一条消息了
    // 因此可能需要统一返回一个 Result 对象，而不是直接在 ResponseEntity 中返回实际类型

    public ResponseEntity<List<ProductDto>> listProductsFallback(Throwable throwable) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

    public ResponseEntity<ProductDto> showProductByIdFallback(Throwable throwable) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

    public ResponseEntity<PageResultDto> getProductsInPageFallback(Throwable throwable) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }
}
