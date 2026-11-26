package com.site.order_service.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// MongoDB document (not a JPA @Entity) -> saved in the "orders" collection
@Document(collection = "orders")
public class Orders {

    // MongoDB generates this id automatically (ObjectId as a String)
    @Id
    private String id;

    // id of the customer who placed the order.
    // Customers live in MySQL (customer_service), so their id is a Long: 1, 2, 3 ...
    private Long customerId;

    private String productName;

    private int quantity;

    private double price;

    public Orders() {
    }

    public Orders(Long customerId, String productName, int quantity, double price) {
        this.customerId = customerId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
