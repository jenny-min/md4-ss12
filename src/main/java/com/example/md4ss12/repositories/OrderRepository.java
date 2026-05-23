package com.example.md4ss12.repositories;

import com.example.md4ss12.models.dto.response.OrderResponseDTO;
import com.example.md4ss12.models.entity.Order;
import com.example.md4ss12.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<OrderResponseDTO> findByUser(User user);

    @Query("""
            SELECT SUM(o.totalMoney)
            FROM Order o
            WHERE YEAR(o.createdDate) = :year
            """)
    Double revenueByYear(int year);

    @Query("""
            SELECT SUM(o.totalMoney)
            FROM Order o
            WHERE MONTH(o.createdDate) = :month
            """)
    Double revenueByMonth(int month);

    @Query("""
            SELECT SUM(o.totalMoney)
            FROM Order o
            WHERE DATE(o.createdDate) = :date
            """)
    Double revenueByDate(LocalDate date);
}
