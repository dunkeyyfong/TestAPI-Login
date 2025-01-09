package com.nhom3.test.repositories;

import com.nhom3.test.entities.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    // Bạn có thể thêm các phương thức query tùy chỉnh nếu cần
}
