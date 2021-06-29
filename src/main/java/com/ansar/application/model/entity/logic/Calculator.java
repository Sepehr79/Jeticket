package com.ansar.application.model.entity.logic;

import java.math.BigDecimal;

public class Calculator {



    private Calculator(){

    }

    /**
     * Throws ArithmeticException, NumberFormatException, NullPointerException
     */
    public static int calculateDiscount(BigDecimal discountPrice, BigDecimal basePrice) {
        BigDecimal zero = new BigDecimal("0");
        if (!discountPrice.equals(zero) && !basePrice.equals(zero))
            return basePrice.subtract(discountPrice).divide(basePrice).multiply(new BigDecimal("100")).intValue();
        return -1;
    }

}
