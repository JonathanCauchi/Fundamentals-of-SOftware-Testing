package edu.uom.currencymanager.currencies;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExchangeRateTest {

    @Test
    public void toString1() {
        ExchangeRate exchangeRate = new ExchangeRate(new Currency("EUR", "Euro", true), new Currency("GBP", "British Pound", true), 1.1);
        assertEquals("EUR  1 = GBP 1.10", exchangeRate.toString());
    }
}