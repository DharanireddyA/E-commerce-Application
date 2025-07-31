package com.example.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.OrderProduct;
import com.example.ecommerce.repository.OrderProductRepository;
import java.util.List;
    @Service
public class OrderProductService {

    @Autowired
    private OrderProductRepository orderProductRepository;

    public List<OrderProduct> getOrdersByProductId(Long productId) {
        return orderProductRepository.findByProductId(productId);
    }
}
