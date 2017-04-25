package com.github.w_kamil.shoppingapp.dao;


import java.math.BigDecimal;

public class Shopping {


    private final String id;
    private final String barCode;
    private String shopIdentifier;
    private final String date;
    private BigDecimal price;

    public Shopping(String id, String barCode, String shopIdentifier, String date, BigDecimal price) {
        this.id = id;
        this.barCode = barCode;
        this.shopIdentifier = shopIdentifier;
        this.date = date;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getBarCode() {
        return barCode;
    }

    public String getShopIdentifier() {
        return shopIdentifier;
    }

    public String getDate() {
        return date;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
