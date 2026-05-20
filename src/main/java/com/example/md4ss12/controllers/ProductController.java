package com.example.md4ss12.controllers;

import com.example.md4ss12.models.entity.Product;
import com.example.md4ss12.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService  productService;

    //mọi user truy cập được
    @GetMapping()
    public List<Product> getProducts() {
        return productService.findAll();
    }

    //Thêm mới - chỉ ADMIN/STAFF
    @PostMapping()
    public Product createProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    //Cập nhật - chỉ ADMIN/STAFF
    @PutMapping("/{id}")
    public Product updateProduct(@RequestBody Product product, @PathVariable Long id) {
        return productService.updateProduct(id, product);
    }

    //Xóa - chỉ ADMIN/STAFF
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "Xóa thành công";
    }
}
