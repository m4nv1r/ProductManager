package com.example.productmanager;

import org.junit.Test;
import static org.junit.Assert.*;

public class MyDBHandlerTest {

    @Test
    public void validProductNameReturnsTrue(){
        assertTrue(MainActivity.isValidProductName("Shirt"));
    }

    @Test
    public void validProductNameReturnsFalse(){
        assertFalse(MainActivity.isValidProductName("23"));
    }

    @Test
    public void validProductPriceReturnsTrue(){
        assertTrue(MainActivity.isValidProductPrice("12.21"));
    }

    @Test
    public void validProductPriceReturnsFalse(){
        assertFalse(MainActivity.isValidProductPrice("hello"));
    }


}
