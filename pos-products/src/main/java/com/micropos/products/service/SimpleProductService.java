package com.micropos.products.service;

import com.micropos.products.model.PageResult;
import com.micropos.products.model.Product;
import com.micropos.products.repository.AmazonRepository;
import com.micropos.products.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleProductService implements ProductService {

    private final ProductRepository productRepository;

    public SimpleProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 存储到名为 products 的 cache 中；注意请求京东失败会返回空列表，此时不应当
    // 缓存结果，否则后续的请求都将请求到空列表
    @Override
    @Cacheable(value = "products", unless = "#result != null && #result.size() > 0")
    public List<Product> products() {
        return productRepository.allProducts();
    }

    // 存储到名为 getProduct 的 cache 中
    @Override
    @Cacheable(value = "getProduct", unless = "#result != null")
    public Product getProduct(String id) {
        return productRepository.findProduct(id);
    }

    @Override
    @Cacheable("getProductsCount")
    public int getProductsCount() {
        return productRepository.productsCount();
    }

    @Override
    @Cacheable("productsInPage")
    public PageResult productsInPage(int page, int pageSize) {
        // 查询商品总数
        int total = getProductsCount();
        // 计算起始位置
        int fromIndex = (page - 1) * pageSize;
        // 获取商品列表
        List<Product> products = productRepository.productsInRange(fromIndex, pageSize);
        return new PageResult(total, products);
    }
}
