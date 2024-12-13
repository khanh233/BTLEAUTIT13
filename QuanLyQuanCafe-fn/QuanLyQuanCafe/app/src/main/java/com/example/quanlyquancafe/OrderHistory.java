package com.example.quanlyquancafe;

import androidx.annotation.NonNull;

import java.util.List;

public class OrderHistory {

    private String username;
    private String address;
    private String phone;

    private List<String> productIds;
    private List<Product> products;

    public OrderHistory() {
    }

    public OrderHistory(String username, String address, String phone, List<String> productIds) {
        this.username = username;
        this.address = address;
        this.phone = phone;
        this.productIds = productIds;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public String toString() {
        return "username " + username +
                "address " + address +
                "phone " + phone +
                "productIds " + productIds +
                "products " + products;
    }
}

