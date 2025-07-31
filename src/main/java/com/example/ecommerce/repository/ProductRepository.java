package com.example.ecommerce.repository;

import com.example.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // 🔍 Custom method to get 5 related products by category, excluding the current product
    List<Product> findTop5ByCategoryAndIdNot(String category, Long id);
    List<Product> findByCategoryIgnoreCase(String category);
    //List<Product> findByCategoryAndIdNot(String category,Long id);
}