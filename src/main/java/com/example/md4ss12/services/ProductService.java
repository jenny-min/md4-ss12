package com.example.md4ss12.services;

import com.example.md4ss12.models.entity.Product;
import com.example.md4ss12.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        Product oldProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        oldProduct.setName(product.getName());
        oldProduct.setPrice(product.getPrice());

        return productRepository.save(oldProduct);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
