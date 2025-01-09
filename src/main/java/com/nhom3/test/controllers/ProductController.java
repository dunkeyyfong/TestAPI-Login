package com.nhom3.test.controllers;

import com.nhom3.test.entities.Product;
import com.nhom3.test.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Lấy danh sách tất cả sản phẩm
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/create-products")
    public String createSampleProducts() {
        List<Product> sampleProducts = Arrays.asList(
                new Product("Laptop", "Electronics", new Date(), 1500.0, "High-performance laptop"),
                new Product("Smartphone", "Electronics", new Date(), 800.0, "Latest model smartphone"),
                new Product("Table", "Furniture", new Date(), 100.0, "Wooden table"),
                new Product("Chair", "Furniture", new Date(), 50.0, "Comfortable chair")
        );

        // Lưu các sản phẩm vào MongoDB
        for (Product product : sampleProducts) {
            productService.createProduct(product);
        }

        return "Sample products created successfully!";
    }

    // Lấy sản phẩm theo ID
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }

    // Thêm sản phẩm mới
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    // Cập nhật sản phẩm
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable String id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    // Xóa sản phẩm
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return "Product with ID " + id + " deleted successfully!";
    }
}
