package com.chemseddinesaidi.ProductService.service;
import com.chemseddinesaidi.ProductService.controller.ProductController;
import com.chemseddinesaidi.ProductService.entity.Product;
import com.chemseddinesaidi.ProductService.exception.ProductServiceCustomException;
import com.chemseddinesaidi.ProductService.model.ProductRequest;
import com.chemseddinesaidi.ProductService.model.ProductResponse;
import com.chemseddinesaidi.ProductService.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;
    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("addProduct method called with productRequest: {}", productRequest);

        Product product = Product.builder()
                .productName(productRequest.getName())
                .quantity(productRequest.getQuantity())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);

        log.info("Product saved successfully with id: {}", product.getProductId());
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(long productId) {
        log.info("getProductById method called with productId: {}", productId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductServiceCustomException("Product not found with id: " + productId, "PRODUCT_NOT_FOUND"));

        ProductResponse productResponse = ProductResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .build();

        return productResponse;
    }
}
