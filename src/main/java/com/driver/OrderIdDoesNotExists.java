package com.driver;

public class OrderIdDoesNotExists extends RuntimeException {
    OrderIdDoesNotExists(String s) {
        super(s);
    }
}
