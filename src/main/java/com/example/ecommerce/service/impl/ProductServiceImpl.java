package com.example.ecommerce.service.impl;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryIgnoreCase(category);
        
    }   
//     @Override
//     public List<Product> getSimilarProducts(Long productId) {
//         Product product = productRepository.findById(productId)
//                 .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
//         return productRepository.findTop5ByCategoryAndIdNot(product.getCategory(), productId);
// }
}