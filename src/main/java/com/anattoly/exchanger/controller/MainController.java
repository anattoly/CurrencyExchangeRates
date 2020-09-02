package com.anattoly.exchanger.controller;

import com.anattoly.exchanger.model.exchange.Currency;
import com.anattoly.exchanger.service.main.CurrencyRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;
import java.util.Set;

@Controller
public class MainController {
    private final CurrencyRatesService currencyRatesService;


    public MainController(CurrencyRatesService currencyRatesService) {
        this.currencyRatesService = currencyRatesService;
    }

    @GetMapping("/live-rates")
    public String filteredRates(Model model) {

        model.addAttribute("calendar", currencyRatesService.getDatesOfCurrencyRates());
        model.addAttribute("currencies", currencyRatesService.getFilteredRates());

        return "index";
    }


}
