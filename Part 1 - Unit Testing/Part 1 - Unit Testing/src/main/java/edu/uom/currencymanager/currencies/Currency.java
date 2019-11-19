package edu.uom.currencymanager.currencies;

import java.util.StringTokenizer;

public class Currency {

    private String code;
    private String name;
    private boolean major;

    public Currency(String code, String name, boolean major) throws IllegalArgumentException {
        //Check format of code
        if (code.length() != 3) {
            throw new IllegalArgumentException("A currency code should have 3 characters.");
            //Check minimum length of name
        } else if (name.length() < 4) {
            throw new IllegalArgumentException("A currency's name should be at least 4 characters long.");
        }
        this.code = code;
        this.name = name;
        this.major = major;
    }

    public static Currency fromString(String currencyString) throws Exception {

        StringTokenizer tokenizer = new StringTokenizer(currencyString, ",");
        String code = tokenizer.nextToken();
        String name = tokenizer.nextToken();
        boolean major = tokenizer.nextToken().equalsIgnoreCase("yes");

        return new Currency(code, name, major);
    }

    public String toString() {
        return code + " - " + name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public boolean isMajor() {
        return major;
    }
}
