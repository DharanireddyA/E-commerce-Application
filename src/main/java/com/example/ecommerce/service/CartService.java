package com.example.ecommerce.service;

import com.example.ecommerce.model.Cart;
import java.util.List;

public interface CartService {
    Cart addCart(Cart cart);             // Add this
    List<Cart> getAllCarts();
    void deleteCartItem(Long id);      // And this
}