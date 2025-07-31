package com.example.ecommerce.service;

import com.example.ecommerce.model.Product;
import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    //List<Product> getSimilarProducts(Long productId);
}