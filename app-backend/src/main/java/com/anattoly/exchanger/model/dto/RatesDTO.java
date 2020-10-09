package com.anattoly.exchanger.model.dto;

import com.anattoly.exchanger.model.exchange.Currency;

import java.util.Map;

public class RatesDTO {
    private String disclaimer;
    private String license;
    private Currency base;
    private Map<Currency, Double> rates;
    private Long timestamp;

    public RatesDTO(String disclaimer, String license, Currency base, Map<Currency, Double> rates, Long timestamp) {
        this.disclaimer = disclaimer;
        this.license = license;
        this.base = base;
        this.rates = rates;
        this.timestamp = timestamp;
    }

    public RatesDTO() {
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public Currency getBase() {
        return base;
    }

    public void setBase(Currency base) {
        this.base = base;
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
