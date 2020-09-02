package com.anattoly.exchanger.service.main;

import com.anattoly.exchanger.model.exchange.Currency;

import java.util.List;
import java.util.Map;

public interface CurrencyRatesService {
    Map<Currency, Map<String, Double>> getFilteredRates();

    List<String> getDatesOfCurrencyRates();
}
