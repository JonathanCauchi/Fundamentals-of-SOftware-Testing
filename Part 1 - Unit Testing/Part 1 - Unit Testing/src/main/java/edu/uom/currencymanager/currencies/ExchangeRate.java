package edu.uom.currencymanager.currencies;

public class ExchangeRate {

    private Currency sourceCurrency;
    private Currency destinationCurrency;
    private double rate;
    private long timeLastChecked;

    public ExchangeRate(Currency sourceCurrency, Currency destinationCurrency, double rate) {
        this.sourceCurrency = sourceCurrency;
        this.destinationCurrency = destinationCurrency;
        this.rate = rate;
        this.timeLastChecked = System.currentTimeMillis();
    }

    public String toString() {
        return getSourceCurrency().getCode() + " 1 = " + getDestinationCurrency().getCode() + " " + Util.formatAmount(getRate());
    }

    public Currency getSourceCurrency() {
        return sourceCurrency;
    }

    public Currency getDestinationCurrency() {
        return destinationCurrency;
    }

    public double getRate() {
        return rate;
    }

    public long getTimeLastChecked() {
        return timeLastChecked;
    }
}
