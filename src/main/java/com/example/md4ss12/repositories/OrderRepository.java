package com.example.md4ss12.repositories;

import com.example.md4ss12.models.dto.response.OrderResponseDTO;
import com.example.md4ss12.models.entity.Order;
import com.example.md4ss12.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<OrderResponseDTO> findByUser(User user);
}
