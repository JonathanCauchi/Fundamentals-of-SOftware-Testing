package edu.uom.currencymanager.currencies;

import java.util.List;

public interface CurrencyDB {
    Currency getCurrencyByCode(String code);

    boolean currencyExists(String code);

    List<Currency> getCurrencies();

    List<Currency> getMajorCurrencies();

    List<ExchangeRate> getMajorCurrencyRates() throws Exception;

    ExchangeRate getExchangeRate(String sourceCurrencyCode, String destinationCurrencyCode) throws Exception;

    void addCurrency(Currency currency) throws Exception;

    void deleteCurrency(String code) throws Exception;
}
