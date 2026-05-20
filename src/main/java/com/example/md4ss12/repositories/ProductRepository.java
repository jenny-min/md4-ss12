package com.example.md4ss12.repositories;

import com.example.md4ss12.models.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
