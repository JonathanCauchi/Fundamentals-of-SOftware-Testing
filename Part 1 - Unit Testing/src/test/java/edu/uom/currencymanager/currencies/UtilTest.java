package edu.uom.currencymanager.currencies;

import org.junit.Test;

import static org.junit.Assert.*;

public class UtilTest {

    @Test
    public void formatAmount() {
        assertEquals("0.00", Util.formatAmount(0d));
        assertEquals("4.40", Util.formatAmount(4.4));
        assertEquals("1,234.57", Util.formatAmount(1234.567d));
    }
}