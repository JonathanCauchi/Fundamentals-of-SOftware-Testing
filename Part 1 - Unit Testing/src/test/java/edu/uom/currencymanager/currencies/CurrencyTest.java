package edu.uom.currencymanager.currencies;

import org.junit.Test;

import static org.junit.Assert.*;

public class CurrencyTest {

    @Test
    public void fromString() throws Exception {
        Currency currency = Currency.fromString("EUR,Euro,yes");
    }

    @Test
    public void toString1() {
        Currency currency = new Currency("EUR", "Euro", true);
        assertEquals("EUR - Euro", currency.toString());
    }
}