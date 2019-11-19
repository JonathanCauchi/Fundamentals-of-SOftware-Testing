package edu.uom.currencymanager.currencies;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class CurrencyDbLookupTest {

    @Before
    public void resetSingleton() throws Exception {
        Field instance = CurrencyDbLookup.class.getDeclaredField("lookup");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Test
    public void getInstance() {
        assertNotNull(CurrencyDbLookup.getInstance());
    }

    @Test
    public void getCurrencyDBNull() {
        CurrencyDbLookup.DB_PATH = "unknown";
        CurrencyDbLookup instance = CurrencyDbLookup.getInstance();
        assertNull(instance.getCurrencyDB());
    }

    @Test
    public void getCurrencyDB() {
        CurrencyDbLookup instance = CurrencyDbLookup.getInstance();
        assertNotNull(instance.getCurrencyDB());
    }
}