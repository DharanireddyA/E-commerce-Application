package com.example.ecommerce.repository;

import com.example.ecommerce.model.OrderProduct;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM OrderProduct op WHERE op.product.id = :productId")
    void deleteByProductId(@Param("productId") Long productId);
    List<OrderProduct> findByProductId(Long productId);
}
