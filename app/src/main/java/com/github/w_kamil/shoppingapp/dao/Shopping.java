package com.github.w_kamil.shoppingapp.dao;


import java.math.BigDecimal;
import java.util.Date;

public class Shopping {


    private final String id;
    private final String barCode;
    private String shopIdentifier;
    private final Date date;
    private BigDecimal price;

    public Shopping(String id, String barCode, String shopIdentifier, Date date, BigDecimal price) {
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

    public Date getDate() {
        return date;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
