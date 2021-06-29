package com.ansar.application.model.entity.beans;

import java.math.BigDecimal;
import java.util.logging.Logger;

public class Product {

    private static final Logger logger = Logger.getLogger(Product.class.getName());

    private String name;
    private String highPrice;
    private String lowPrice;
    private String count;


    public Product(String name, String highPrice, String lowPrice, String count) {
        setName(name);
        setHighPrice(highPrice);
        setLowPrice(lowPrice);
        setCount(count);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHighPrice(String highPrice) {
        if (highPrice.trim().matches("[0-9]*\\.?[0-9]*"))
            this.highPrice = highPrice;
        else
            throw new IllegalArgumentException("You must enter a number");
    }

    public void setLowPrice(String lowPrice) {
        if (lowPrice.trim().matches("[0-9]*\\.?[0-9]*"))
            this.lowPrice = lowPrice;
        else
            throw new IllegalArgumentException("You must enter a number");
    }

    public BigDecimal getCount() {
        return new BigDecimal(count);
    }

    public void setCount(String count) {
        if (count.trim().matches("[0-9]*\\.?[0-9]*"))
            this.count = count;
        else
            throw new IllegalArgumentException("You must enter a number");
    }

    public String getName() {
        if (Float.parseFloat(count) > 1)
            return name + " " + String.valueOf( getCount().intValue() ) + " عدد ";
        else if (Float.parseFloat(count) < 1){
            return name + " " + String.valueOf( (int) (getCount().floatValue() * 1000) )+ " گرمی ";
        }
        else
            return name;
    }

    public BigDecimal getHighPrice() {
        BigDecimal highPrice = new BigDecimal(this.highPrice);
        BigDecimal count = new BigDecimal(this.count);

        return highPrice.multiply(count);
    }

    public BigDecimal getLowPrice() {
        BigDecimal lowPrice = new BigDecimal(this.lowPrice);
        BigDecimal count = new BigDecimal(this.count);

        return lowPrice.multiply(count);
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", highPrice='" + highPrice + '\'' +
                ", lowPrice='" + lowPrice + '\'' +
                '}';
    }
}