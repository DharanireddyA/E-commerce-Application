package com.example.ecommerce.service;
import com.example.ecommerce.model.Order;
import java.util.List;
public interface OrderService {
    Order placeOrder(Order order);
    List<Order> getAllOrders();
    Order getOrderById(Long id);
    Order saveOrder(Order order);
    // For deleting the entire order
public void deleteOrder(Long id);
public void removeProductFromOrder(Long orderId, Long productId);
public Order placeOrder(Long userId, List<Long> productIds);
}