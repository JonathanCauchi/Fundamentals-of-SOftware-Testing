package edu.uom.currencymanager.currencies;

import edu.uom.currencymanager.currencyserver.CurrencyServer;
import edu.uom.currencymanager.currencyserver.DefaultCurrencyServer;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CurrencyDatabase implements CurrencyDB {
    public static long FIVE_MINUTES_IN_MILLIS = 300000;  //5*60*100
    private CurrencyServer currencyServer;
    private List<Currency> currencies = new ArrayList<Currency>();
    private HashMap<String, ExchangeRate> exchangeRates = new HashMap<String, ExchangeRate>();

    private String currenciesFile;

    public CurrencyDatabase(String currenciesFilePath) throws Exception {
        this.currenciesFile = currenciesFilePath;
        init();
    }

    private void init() throws Exception {
        //Initialise currency server
        currencyServer = new DefaultCurrencyServer();

        //Read in supported currencies from text file
        BufferedReader reader = new BufferedReader(new FileReader(currenciesFile));

        //skip the first line to avoid header
        String firstLine = reader.readLine();
        if (!firstLine.equals("code,name,major")) {
            throw new Exception("Parsing error when reading currencies file.");
        }

        while (reader.ready()) {
            String nextLine = reader.readLine();

            //Check if line has 2 commas
            int numCommas = 0;
            char[] chars = nextLine.toCharArray();
            for (char c : chars) {
                if (c == ',') {
                    numCommas++;
                }
            }

            if (numCommas != 2) {
                throw new Exception("Parsing error: expected two commas in line " + nextLine);
            }

            Currency currency = Currency.fromString(nextLine);
            if (!currencyExists(currency.getCode())) {
                currencies.add(currency);
            }
        }
    }

    public Currency getCurrencyByCode(String code) {

        for (Currency currency : currencies) {
            if (currency.getCode().equalsIgnoreCase(code)) {
                return currency;
            }
        }

        return null;
    }

    public boolean currencyExists(String code) {
        return getCurrencyByCode(code) != null;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public List<Currency> getMajorCurrencies() {
        List<Currency> result = new ArrayList<Currency>();

        for (Currency currency : currencies) {
            if (currency.isMajor()) {
                result.add(currency);
            }
        }

        return result;
    }

    public List<ExchangeRate> getMajorCurrencyRates() throws Exception {

        List<ExchangeRate> exchangeRates = new ArrayList<ExchangeRate>();

        List<Currency> currencies = getMajorCurrencies();

        for (Currency src : currencies) {
            for (Currency dst : currencies) {
                if (src != dst) {
                    exchangeRates.add(getExchangeRate(src.getCode(), dst.getCode()));
                }
            }
        }
        return exchangeRates;
    }

    public ExchangeRate getExchangeRate(String sourceCurrencyCode, String destinationCurrencyCode) throws Exception {
        ExchangeRate result = null;

        Currency sourceCurrency = getCurrencyByCode(sourceCurrencyCode);
        if (sourceCurrency == null) {
            throw new Exception("Unkown currency: " + sourceCurrencyCode);
        }

        Currency destinationCurrency = getCurrencyByCode(destinationCurrencyCode);
        if (destinationCurrency == null) {
            throw new Exception("Unkown currency: " + destinationCurrencyCode);
        }

        //Check if exchange rate exists in database
        String key = sourceCurrencyCode + destinationCurrencyCode;
        if (exchangeRates.containsKey(key)) {
            result = exchangeRates.get(key);
            if (System.currentTimeMillis() - result.getTimeLastChecked() > FIVE_MINUTES_IN_MILLIS) {
                result = null;
            }
        }

        if (result == null) {
            double rate = currencyServer.getExchangeRate(sourceCurrencyCode, destinationCurrencyCode);
            result = new ExchangeRate(sourceCurrency, destinationCurrency, rate);

            //Cache exchange rate
            exchangeRates.put(key, result);

            //Cache inverse exchange rate
            String inverseKey = destinationCurrencyCode + sourceCurrencyCode;
            exchangeRates.put(inverseKey, new ExchangeRate(destinationCurrency, sourceCurrency, 1 / rate));
        }

        return result;
    }

    public void addCurrency(Currency currency) throws Exception {
        //Check if currency already exists
        String code = currency.getCode();
        if (currencyExists(code)) {
            throw new InvalidObjectException("The currency " + code + " already exists.");
        }
        //Save to list
        currencies.add(currency);

        //Persist
        persist();
    }

    public void deleteCurrency(String code) throws Exception {

        if (!currencyExists(code)) {
            throw new IllegalArgumentException("Currency does not exist: " + code);
        }
        //Save to list
        currencies.remove(getCurrencyByCode(code));

        //Persist
        persist();
    }

    private void persist() throws Exception {
        //Persist list
        BufferedWriter writer = new BufferedWriter(new FileWriter(currenciesFile));

        writer.write("code,name,major\n");
        for (Currency currency : currencies) {
            writer.write(currency.getCode() + "," + currency.getName() + "," + (currency.isMajor() ? "yes" : "no"));
            writer.newLine();
        }

        writer.flush();
        writer.close();
    }

}
