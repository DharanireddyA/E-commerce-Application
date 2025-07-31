package com.example.ecommerce.service;

import com.example.ecommerce.model.Cart;
//import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Cart addCart(Cart cart) {
        // Automatically fetch product details using productId
        if (cart.getProductId() != null) {
            productRepository.findById(cart.getProductId()).ifPresent(product -> {
                cart.setProductName(product.getName());
                cart.setImage(product.getImage());
                cart.setPrice(product.getPrice());
            });
        }
        return cartRepository.save(cart);
    }

    @Override
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }
    @Override
    public void deleteCartItem(Long id) {
        cartRepository.deleteById(id);
    }
}