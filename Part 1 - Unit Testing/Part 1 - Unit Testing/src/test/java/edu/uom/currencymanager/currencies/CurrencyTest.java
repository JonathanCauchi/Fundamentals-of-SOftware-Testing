package edu.uom.currencymanager.currencies;

import org.junit.Test;

import static org.junit.Assert.*;

public class CurrencyTest {

    @Test
    public void fromString() throws Exception {
        Currency currency = Currency.fromString("EUR,Euro,yes");
        assertEquals("EUR", currency.getCode());
        assertEquals("Euro", currency.getName());
        assertTrue(currency.isMajor());
    }

    @Test(expected = IllegalArgumentException.class)
    public void fromStringWrongCode() throws Exception {
        new Currency("EURO", "Euro", true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void fromStringWrongName() throws Exception {
        Currency.fromString("EUR,Eur,yes");
    }

    @Test
    public void toString1() {
        assertEquals("EUR - Euro", new Currency("EUR", "Euro", true).toString());
    }
}