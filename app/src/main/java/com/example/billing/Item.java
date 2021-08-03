package com.example.billing;

public class Item {
    String name;
    String price;
    String serial;

    public Item(String name, String price, String serial) {
        this.name = name;
        this.price = price;
        this.serial = serial;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getSerial() {
        return serial;
    }
}
