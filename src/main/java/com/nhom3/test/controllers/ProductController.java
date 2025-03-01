package com.nhom3.test.controllers;

import com.nhom3.test.entities.Product;
import com.nhom3.test.services.ProductService;
import com.nhom3.test.security.JWTUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private JWTUtil jwtUtil;


    // Lấy danh sách tất cả sản phẩm (Secured with JWT)
    @GetMapping
    public List<Product> getAllProducts(HttpServletRequest request) {
        validateToken(request);
        return productService.getAllProducts();
    }

    // Lấy sản phẩm theo ID (Secured with JWT)
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable String id, HttpServletRequest request) {
        validateToken(request);
        return productService.getProductById(id);
    }

    // Thêm sản phẩm mới (Secured with JWT)
    @PostMapping
    public Product createProduct(@RequestBody Product product, HttpServletRequest request) {
        validateToken(request);
        return productService.createProduct(product);
    }

    // Cập nhật sản phẩm (Secured with JWT)
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable String id, @RequestBody Product product, HttpServletRequest request) {
        validateToken(request);
        return productService.updateProduct(id, product);
    }

    // Xóa sản phẩm (Secured with JWT)
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable String id, HttpServletRequest request) {
        validateToken(request);
        productService.deleteProduct(id);
        return "Product with ID " + id + " deleted successfully!";
    }

    // Utility method to validate the token
    private void validateToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Authorization header is missing or invalid");
        }

        String token = authorizationHeader.substring(7);
        System.out.println("Received Token: " + token);

        if (!jwtUtil.validateToken(token)) {
            throw new RuntimeException("Invalid JWT token");
        }

        Claims claims = jwtUtil.extractClaims(token);
        System.out.println("JWT Claims: " + claims);
    }
}
