package com.micropos.products.service;

import com.micropos.products.model.PageResult;
import com.micropos.products.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> products();

    Product getProduct(String id);

    int getProductsCount();

    PageResult productsInPage(int page, int pageSize);
}
