package edu.uom.currencymanager.currencies;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class CurrencyDatabaseTests {

    private CurrencyDatabase cd;

    @Before
    public void setup() throws Exception {
        cd = new CurrencyDatabase();
    }

    @After
    public void teardown() {
        cd = null;
    }

    @Test
    public void init() {
        try {
            cd.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getCurrencyByCode() {
        assertNull(cd.getCurrencyByCode("XYZ"));
        assertNotNull(cd.getCurrencyByCode("EUR"));
    }

    @Test
    public void currencyExists() {
        assertTrue(cd.currencyExists("EUR"));
        assertFalse(cd.currencyExists("XYZ"));
    }

    @Test
    public void getCurrencies() {
        assertNotNull(cd.getCurrencies());
    }

    @Test
    public void getMajorCurrencies() {
        assertNotNull(cd.getMajorCurrencies());
    }

    @Test
    public void getExchangeRate() throws Exception {
        assertNotNull(cd.getExchangeRate("GBP", "EUR"));
        Thread.sleep(TimeUnit.MINUTES.toMillis(5) + 1);
        assertNotNull(cd.getExchangeRate("GBP", "EUR"));
    }

    @Test(expected = Exception.class)
    public void getExchangeRateWrongSource() throws Exception {
        cd.getExchangeRate("XYZ", "EUR");
    }

    @Test(expected = Exception.class)
    public void getExchangeRateWrongDestination() throws Exception {
        cd.getExchangeRate("GBP", "XYZ");
    }

    @Test
    public void addCurrency() throws Exception {
        cd.addCurrency(new Currency("BTC", "Bitcoin", true));
    }

    @Test
    public void deleteCurrency() throws Exception {
        cd.deleteCurrency("BTC");
    }

    @Test
    public void persist() throws Exception {
        cd.persist();
    }

}