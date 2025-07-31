package com.example.ecommerce.controller;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
    return ResponseEntity.ok(productService.getProductsByCategory(category));
}
    @Autowired
    private ProductRepository productRepository;

    // 🔄 Create Product
    @PostMapping
    public Product saveProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    // 📄 Get All Products
    @GetMapping
    public List<Product> getAllProducts() {
        List<Product> all= productRepository.findAll();
        System.out.println("Returning products count: "+all.size());
        return all;
    }

    // 🔍 Get Product by ID
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productRepository.findById(id).orElse(null);
    }

    // 🗑 Delete Product by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        try{
            Optional<Product> product=productRepository.findById(id);
            if(product.isPresent()){
                productRepository.deleteById(id);
                return ResponseEntity.ok("Product deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting product: " + e.getMessage());
        }
    }

    // ⭐ NEW: Get Related Products (same category, excluding current product)
    @GetMapping("/related/{id}")
    public List<Product> getRelatedProducts(@PathVariable Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return new ArrayList<>();
        }


        return productRepository.findTop5ByCategoryAndIdNot(product.getCategory(), id);
    }
    // @GetMapping("/{id}/similar")
    // public ResponseEntity<List<Product>> getSimilarProducts(@PathVariable Long id) {
    //     //List<Product> similarProducts = productService.getSimilarProducts(id);
    //     return ResponseEntity.ok(productService.getSimilarProducts(id));
    // }
    
}