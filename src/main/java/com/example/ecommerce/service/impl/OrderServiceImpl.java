package com.example.ecommerce.service.impl;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.model.User;
import com.example.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public Order placeOrder(Order order) {
        return orderRepository.save(order);
    }
    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);   
    }
    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }
    @Override
    public void deleteOrder(Long id) {
        if(!orderRepository.existsById(id)) {
            throw new RuntimeException("Order with ID " + id + " does not exist.");
        }
        orderRepository.deleteById(id);
    }
    @Override
    public void removeProductFromOrder(Long orderId, Long productId) {
        Order order = getOrderById(orderId);
        if (order == null) {
            throw new RuntimeException("Order with ID " + orderId + " does not exist.");
        }
        order.getProducts().removeIf(product -> product.getId().equals(productId));
        orderRepository.save(order);
    }
    @Override
public Order placeOrder(Long userId, List<Long> productIds) {
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

    List<Product> products = productRepository.findAllById(productIds);

    if (products.isEmpty()) {
        throw new RuntimeException("No products found for the given IDs");
    }

    Order order = new Order();
    order.setUser(user);
    order.setProducts(products);
    order.setOrderDate(LocalDateTime.now());

    return orderRepository.save(order);
}

}