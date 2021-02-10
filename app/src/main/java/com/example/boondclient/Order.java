package com.example.boondclient;

public class Order {
    String Price, Quantity, Size, Status, Number;

    public Order(String price, String quantity, String size, String status, String Number) {
        Price = price;
        Quantity = quantity;
        Size = size;
        Status = status;
        this.Number = Number;
    }

    public Order() {
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String Number) {
        this.Number = Number;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
