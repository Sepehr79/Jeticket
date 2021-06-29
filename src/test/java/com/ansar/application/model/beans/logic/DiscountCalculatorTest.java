package com.ansar.application.model.beans.logic;

import com.ansar.application.model.entity.logic.Calculator;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class DiscountCalculatorTest {

    @Test
    public void testCalculateDiscount(){
        int discount = Calculator.calculateDiscount(new BigDecimal("90"), new BigDecimal("100"));
        Assert.assertEquals(discount, 10);
    }

}
