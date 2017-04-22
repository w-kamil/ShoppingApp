package com.github.w_kamil.shoppingapp.dao;


import java.math.BigDecimal;
import java.util.Date;

public class Shopping {

    private final String barCode;
    private String identifier;
    private final Date date;
    private BigDecimal price;

    public Shopping(String barCode, String identifier, Date date, BigDecimal price) {
        this.barCode = barCode;
        this.identifier = identifier;
        this.date = date;
        this.price = price;
    }

    public String getBarCode() {
        return barCode;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Date getDate() {
        return date;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
