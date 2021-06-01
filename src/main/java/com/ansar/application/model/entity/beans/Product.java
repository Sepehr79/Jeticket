package com.ansar.application.model.entity.beans;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.logging.Logger;

public class Product {

    private static final Logger logger = Logger.getLogger(Product.class.getName());

    private String name;
    private BigDecimal highPrice;
    private BigDecimal lowPrice;
    private BigDecimal count;


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
            this.highPrice = new BigDecimal(highPrice);
        else
            throw new IllegalArgumentException("You must enter a number");
    }

    public void setLowPrice(String lowPrice) {
        if (lowPrice.trim().matches("[0-9]*\\.?[0-9]*"))
            this.lowPrice = new BigDecimal(lowPrice.trim());
        else
            throw new IllegalArgumentException("You must enter a number");
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(String count) {
        if (count.trim().matches("[0-9]*\\.?[0-9]*"))
            this.count = new BigDecimal(count);
        else
            throw new IllegalArgumentException("You must enter a number");
    }

    public String getName() {
        if (count.intValue() > 1)
            return name + " " + count.intValue() + " عدد ";
        else if (count.floatValue() < 1){
            return name + " " + count.multiply(new BigDecimal("1000")).intValue() + " گرمی ";
        }
        else
            return name;
    }

    public int getHighPrice() {
        return highPrice.multiply(count).intValue();
    }

    public int getLowPrice() {
        return lowPrice.multiply(count).intValue();
    }

    public int getDiscount() {
        try {

            logger.info("High price: " + highPrice);
            logger.info("Low price: " + lowPrice);

            BigDecimal calculating = highPrice.subtract(lowPrice);

            logger.info("Subtracted price:" + calculating);

            calculating = calculating.divide(highPrice, MathContext.DECIMAL128);

            logger.info("Divided price: " + calculating);

            calculating = calculating.multiply(new BigDecimal("100"));

            logger.info("Final: " + calculating);

            return calculating.intValue();
        }catch (NumberFormatException exception){
            exception.printStackTrace();
            logger.info("Exception in calculating discount");
        }
        return -1;
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