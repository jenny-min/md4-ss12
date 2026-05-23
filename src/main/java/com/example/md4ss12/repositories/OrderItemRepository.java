package com.example.md4ss12.repositories;

import com.example.md4ss12.models.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("""
            SELECT COUNT(od) > 0
            FROM OrderItem od
            WHERE od.order.user.id = :userId
            AND od.product.id = :productId
            """)
    boolean hasPurchased(
            Long userId,
            Long productId
    );
}
