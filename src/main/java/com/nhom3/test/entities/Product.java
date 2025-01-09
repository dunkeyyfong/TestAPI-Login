package com.nhom3.test.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "products") // Tạo collection MongoDB với tên "products"
public class Product {

    @Id
    private String id; // MongoDB sẽ tự động sinh giá trị này
    private String name;
    private String category;
    private Date createdDate;
    private double price;
    private String desc;

    // Constructor không tham số (Spring cần)
    public Product() {}

    // Constructor đầy đủ tham số
    public Product(String name, String category, Date createdDate, double price, String desc) {
        this.name = name;
        this.category = category;
        this.createdDate = createdDate;
        this.price = price;
        this.desc = desc;
    }

    // Getters và Setters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
