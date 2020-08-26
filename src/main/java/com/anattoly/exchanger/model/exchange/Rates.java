package com.anattoly.exchanger.model.exchange;

import java.util.Map;

public class Rates {
    private Currency baseCurrency;
    private Map<Currency, Double> rates;
    private Long timestamp;

    public Rates(Currency baseCurrency, Map<Currency, Double> rates, Long timestamp) {
        this.baseCurrency = baseCurrency;
        this.rates = rates;
        this.timestamp = timestamp;
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(Currency baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public Map<Currency, Double> getRates() {
        return rates;
    }

    public void setRates(Map<Currency, Double> rates) {
        this.rates = rates;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
