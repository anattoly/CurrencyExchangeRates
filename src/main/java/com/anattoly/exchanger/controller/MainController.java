package com.anattoly.exchanger.controller;

import com.anattoly.exchanger.exeption.ResourceNotFoundExeption;
import com.anattoly.exchanger.model.dto.RatesDTO;
import com.anattoly.exchanger.model.exchange.Currency;
import com.anattoly.exchanger.model.exchange.Rates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class MainController {
    private static final Map<String, String> CURRENCY_NAMES = new HashMap<>();

    @Value("${app.id}")
    private String appId;

    @Value("${api.url.latest}")
    private String api_url_latest;

    @Value("${api.url.historical}")
    private String api_url_historical;

    @Value("${currency.filter}")
    private String currency_filter;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/live-rates")
    public String filteredRates(Model model) {
        Map<Currency, Map<String, Double>> historicalRates = new LinkedHashMap<>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date currentDate = new Date();
        int countDays = 10;

        List<String> listDates = new LinkedList<>();
        List<Rates> ratesList = new ArrayList<>();

        /*loop for add history rates*/
        for (int i = 0; i <= countDays; i++) {
            Date historicalDate = new Date(currentDate.getTime() - i * 24 * 3600 * 1000);
            String formatHistoryDate = dateFormat.format(historicalDate);

            if (formatHistoryDate.equals(dateFormat.format(currentDate))) {
                RatesDTO ratesDTO = getRatesDTO(api_url_latest, appId);
                Rates rates = createRates(ratesDTO);

                ratesList.add(rates);
                listDates.add(dateFormat.format(currentDate));

            } else {
                RatesDTO ratesDTO = getRatesDTO(api_url_historical.concat(formatHistoryDate), appId);
                Rates rates = createRates(ratesDTO);

                ratesList.add(rates);

                listDates.add(formatHistoryDate);

                /*get currencies name*/
                rates.getRates().forEach((key, value) -> {
                    String currKey = key.name();
                    String currName = key.getName();
                    CURRENCY_NAMES.put(currKey, currName);
                });
            }
        }

        String[] currencyFilteredList = currency_filter.split(", ");

        /*filling map for table rates*/
        for (String abbreviation : currencyFilteredList) {
            Map<String, Double> ratesPerDate = new LinkedHashMap<>();
            for (Rates rates : ratesList) {
                ratesPerDate.put(dateFormat.format(rates.getTimestamp() * 1000),
                        rates.getRates().get(Currency.valueOf(abbreviation)));
            }
            historicalRates.put(Currency.valueOf(abbreviation), ratesPerDate);
        }
        model.addAttribute("calendar", listDates);
        model.addAttribute("currencyNames", CURRENCY_NAMES);
        model.addAttribute("currencies", historicalRates);

        return "index";
    }

    private RatesDTO getRatesDTO(String apiUrl, String apiID) {
        RatesDTO ratesDTO = restTemplate.getForObject(apiUrl.concat(apiID), RatesDTO.class);
        Optional.ofNullable(ratesDTO).orElseThrow(
                () -> new ResourceNotFoundExeption("API url: " + apiUrl + " or AppKey: " + apiID + " wrong!"));
        return ratesDTO;
    }

    private Rates createRates(RatesDTO ratesDTO) {
        return new Rates(ratesDTO.getBase(),
                ratesDTO.getRates(),
                ratesDTO.getTimestamp());
    }
}
