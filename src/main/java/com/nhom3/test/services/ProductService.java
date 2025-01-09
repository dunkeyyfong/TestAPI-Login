package com.nhom3.test.services;

import com.nhom3.test.entities.Product;
import com.nhom3.test.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Lấy danh sách tất cả sản phẩm
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Lấy sản phẩm theo ID
    public Product getProductById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    // Thêm sản phẩm mới
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // Cập nhật sản phẩm
    public Product updateProduct(String id, Product product) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct != null) {
            existingProduct.setName(product.getName());
            existingProduct.setCategory(product.getCategory());
            existingProduct.setCreatedDate(product.getCreatedDate());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setDesc(product.getDesc());
            return productRepository.save(existingProduct);
        }
        return null;
    }

    // Xóa sản phẩm
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
}
