package com.example.boondclient;

public class Size {
    String ID,Price,Size, Number;

    public Size(String ID, String price, String size, String number) {
        this.ID = ID;
        Price = price;
        Size = size;
        Number = number;
    }

    public Size(){

    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }
}
