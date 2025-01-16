package com.nhom3.test.controllers;

import com.nhom3.test.entities.Order;
import com.nhom3.test.entities.Product;
import com.nhom3.test.services.OrderService;
import com.nhom3.test.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        // Fetch full product details using product IDs from the request
        List<Product> products = order.getProducts().stream()
                .map(product -> {
                    Product fullProduct = productService.getProductById(product.getId());
                    if (fullProduct == null) {
                        throw new RuntimeException("Product not found for ID: " + product.getId());
                    }
                    return fullProduct;
                })
                .collect(Collectors.toList());

        // Set the fetched product details in the order
        order.setProducts(products);

        // Calculate the total price
        double totalPrice = products.stream().mapToDouble(Product::getPrice).sum();
        order.setTotalPrice(totalPrice);

        // Set the current time as the createdDate if not provided
        if (order.getCreatedDate() == null) {
            order.setCreatedDate(new Date());
        }

        // Save and return the order
        return orderService.createOrder(order);
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable String id) {
        return orderService.getOrderById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable String id) {
        orderService.deleteOrder(id);
        return "Order with ID " + id + " deleted successfully!";
    }
}
