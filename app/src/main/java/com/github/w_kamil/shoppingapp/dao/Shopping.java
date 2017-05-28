package com.github.w_kamil.shoppingapp.dao;


import java.math.BigDecimal;
import java.util.Date;

public class Shopping {


    private final String id;
    private Product product;
    private Shop shop;
    private final Date date;
    private BigDecimal price;

    public Shopping(String id, Product product, Shop shop, Date date, BigDecimal price) {
        this.id = id;
        this.product = product;
        this.shop = shop;
        this.date = date;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Date getDate() {
        return date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
