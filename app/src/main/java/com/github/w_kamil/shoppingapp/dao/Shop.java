package com.github.w_kamil.shoppingapp.dao;


public class Shop {

    private final int id;
    private String identifier;
    private String address;

    public Shop(int id, String identifier, String address) {
        this.id = id;
        this.identifier = identifier;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
