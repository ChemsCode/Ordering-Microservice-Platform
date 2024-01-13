package com.chemseddinesaidi.ProductService.service;

import com.chemseddinesaidi.ProductService.model.ProductRequest;
import com.chemseddinesaidi.ProductService.model.ProductResponse;

public interface ProductService {
    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long productId);

    void reduceQuantity(long productId, long quantity);
}
