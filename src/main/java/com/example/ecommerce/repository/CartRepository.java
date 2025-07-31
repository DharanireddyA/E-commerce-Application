package com.example.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ecommerce.model.Cart;
import java.util.List;


public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart>  findByUserId(Long userId);
}