package com.example.ecommerce.controller;
import com.example.ecommerce.model.OrderProduct;
import java.util.List;
import com.example.ecommerce.repository.OrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
//import com.example.ecommerce.service.OrderProductService;

@RestController
@RequestMapping("/api/order-products")
public class OrderProductController {

    @Autowired
    private OrderProductRepository orderProductRepository;
    // @Autowired
    // private OrderProductService orderProductService;

    @GetMapping("/{productId}")
    public ResponseEntity<List<OrderProduct>> getOrderProductsByProductId(@PathVariable Long productId) {
        List<OrderProduct> orderProducts = orderProductRepository.findByProductId(productId);
        // if (orderProducts.isEmpty()) {
        //     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        // }
        return ResponseEntity.ok(orderProducts);
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteOrderProductsByProductId(@PathVariable Long productId) {
        try {
            orderProductRepository.deleteByProductId(productId);
            return ResponseEntity.ok("Deleted order-product links for product " + productId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error: " + e.getMessage());
        }
    }
}
