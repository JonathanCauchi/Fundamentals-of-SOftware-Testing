package edu.uom.currencymanager.currencies;

import org.junit.Test;

import java.io.InvalidObjectException;

import static org.junit.Assert.*;

public class CurrencyDatabaseTests {

    private CurrencyDB cd = CurrencyDbLookup.getInstance().getCurrencyDB();

//    @Before
//    public void setup() {
//        cd = CurrencyDbLookup.getInstance().getCurrencyDB();
//    }

//    @After
//    public void teardown() {
//        cd = null;
//    }

    @Test(expected = Exception.class)
    public void errorFile1() throws Exception {
        new CurrencyDatabase("target/classes/currencies-header-error.txt");
    }

    @Test(expected = Exception.class)
    public void errorFile2() throws Exception {
        new CurrencyDatabase("target/classes/currencies-commas-error.txt");
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
        //changing the expiry time to speed up the test
        CurrencyDatabase.FIVE_MINUTES_IN_MILLIS = 50;
        assertNotNull(cd.getExchangeRate("GBP", "EUR"));
        Thread.sleep(51);
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
    public void getMajorCurrencyRates() throws Exception {
        assertFalse(cd.getMajorCurrencyRates().isEmpty());
    }

    @Test(expected = InvalidObjectException.class)
    public void addCurrency() throws Exception {
        cd.addCurrency(new Currency("EUR", "Euro", true));
    }

    @Test
    public void addDeleteCurrency() throws Exception {
        if (!cd.currencyExists("BTC")) {
            cd.addCurrency(new Currency("BTC", "Bitcoin", true));
            cd.deleteCurrency("BTC");
        } else {
            cd.deleteCurrency("BTC");
            cd.addCurrency(new Currency("BTC", "Bitcoin", true));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteCurrencyExc() throws Exception {
        cd.deleteCurrency("XYZ");
    }
}