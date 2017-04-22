package com.github.w_kamil.shoppingapp.dao;


public class Product {

    private final String barCode;
    private String description;


    public Product(String barCode, String description) {
        this.barCode = barCode;
        this.description = description;
    }

    public String getBarCode() {
        return barCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
