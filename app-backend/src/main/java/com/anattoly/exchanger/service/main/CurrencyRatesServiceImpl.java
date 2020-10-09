package com.anattoly.exchanger.service.main;

import com.anattoly.exchanger.exeption.ResourceNotFoundExeption;
import com.anattoly.exchanger.model.dto.RatesDTO;
import com.anattoly.exchanger.model.exchange.Currency;
import com.anattoly.exchanger.model.exchange.Rates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CurrencyRatesServiceImpl implements CurrencyRatesService {
    private static final List<String> LIST_DATES = new LinkedList<>();


    @Value("${app.id}")
    private String appId;

    @Value("${api.url.latest}")
    private String api_url_latest;

    @Value("${api.url.historical}")
    private String api_url_historical;

    @Value("${currency.symbols}")
    private String currency_symbols;

    @Value("${app.day.of.currency.rates}")
    private Integer countDays;

    @Autowired
    private RestTemplate restTemplate;



    @Override
    public Map<Currency, Map<String, Double>> getFilteredRates() {
        Map<Currency, Map<String, Double>> historicalRates = new LinkedHashMap<>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date currentDate = new Date();

        List<Rates> ratesList = new ArrayList<>();

        /*loop for add history rates*/
        for (int i = 0; i <= countDays; i++) {
            Date historicalDate = new Date(currentDate.getTime() - i * 24 * 3600 * 1000);
            String formatHistoryDate = dateFormat.format(historicalDate);

            if (formatHistoryDate.equals(dateFormat.format(currentDate))) {
                RatesDTO ratesDTO = getRatesDTO(api_url_latest, appId);
                Rates rates = createRates(ratesDTO);

                ratesList.add(rates);
                LIST_DATES.add(dateFormat.format(currentDate));

            } else {
                RatesDTO ratesDTO = getRatesDTO(api_url_historical.concat(formatHistoryDate), appId);
                Rates rates = createRates(ratesDTO);

                ratesList.add(rates);

                LIST_DATES.add(formatHistoryDate);
            }
        }

        String[] currencyFilteredList = currency_symbols.split(", ");

        /*filling map for table rates*/
        for (String abbreviation : currencyFilteredList) {
            Map<String, Double> ratesPerDate = new LinkedHashMap<>();
            for (Rates rates : ratesList) {
                ratesPerDate.put(dateFormat.format(rates.getTimestamp() * 1000),
                        rates.getRates().get(Currency.valueOf(abbreviation)));
            }
            historicalRates.put(Currency.valueOf(abbreviation), ratesPerDate);
        }


        return historicalRates;
    }

    @Override
    public List<String> getDatesOfCurrencyRates() {
        return LIST_DATES;
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
