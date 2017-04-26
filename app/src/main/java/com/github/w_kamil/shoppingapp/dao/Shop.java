package com.github.w_kamil.shoppingapp.dao;


public class Shop {

    private String identifier;
    private String address;

    public Shop(String identifier, String address) {
        this.identifier = identifier;
        this.address = address;
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
