package edu.uom.currencymanager.currencies;

public class CurrencyDbLookup {
    public static String DB_PATH = "target/classes/currencies.txt";
    private static CurrencyDbLookup lookup;
    private CurrencyDB currencyDB;

    private CurrencyDbLookup() {
        //setup the CurrencyDatabase
        try {
            currencyDB = new CurrencyDatabase(DB_PATH);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static CurrencyDbLookup getInstance() {
        if (lookup == null) {
            lookup = new CurrencyDbLookup();
        }
        return lookup;
    }

    public CurrencyDB getCurrencyDB() {
        return currencyDB;
    }
}
