package com.devbramm.mitumba.Models;

import java.util.List;

public class OrderRequest {
    private String phone;
    private String name;
    private String address;
    private String total;
    private List<Cart> products;
    private String paymentTransactionID;
    private String paymentStatus;
    private String paymentMode;

    public OrderRequest() {
    }

    public OrderRequest(String name, String phone, String address, String total, String paymentTransactionID, String paymentStatus, String paymentMode, List<Cart> products) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.total = total;
        this.paymentTransactionID = paymentTransactionID;
        this.paymentStatus = paymentStatus;
        this.paymentMode = paymentMode;
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPaymentTransactionID() {
        return paymentTransactionID;
    }

    public void setPaymentTransactionID(String paymentTransactionID) {
        this.paymentTransactionID = paymentTransactionID;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public List<Cart> getProducts() {
        return products;
    }

    public void setProducts(List<Cart> products) {
        this.products = products;
    }
}
