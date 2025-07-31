package com.example.ecommerce.controller;

import com.example.ecommerce.dto.AddToCartRequest;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins="http://127.0.0.1.5500")
@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    // ✅ POST: Add to cart using full details
    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(@RequestBody AddToCartRequest request) {
        Cart cart = new Cart();

        cart.setUserId(request.getUserId());
        cart.setProductId(request.getProductId());
        cart.setProductName(request.getProductName());
        cart.setImage(request.getImage());
        cart.setPrice(request.getPrice());
        cart.setQuantity(request.getQuantity());

        Cart savedCart = cartService.addCart(cart);
        return ResponseEntity.ok(savedCart);
    }

    // ✅ GET: All carts (API)
    @GetMapping
    public List<Cart> getAllCarts() {
        return cartService.getAllCarts();
    }

    // ✅ GET: Web view cart for user
    @GetMapping("/cart")
    public String viewCart(Model model) {
        Long userId = 1L; // 🔁 Replace with session user if needed
        List<Cart> cartItems = cartRepository.findByUserId(userId);
        model.addAttribute("cartItems", cartItems);
        return "cart"; // cart.html in templates
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id) {  
        cartService.deleteCartItem(id);
        return ResponseEntity.noContent().build();
    }
}