package com.jgrajber.task.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jgrajber.task.exception.NoSuchCurrencyCodeException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class CurrencyServiceNBPWebAPIImpl implements CurrencyService {

    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    private static final String URI = "http://api.nbp.pl/api/exchangerates/tables/A?format=json";

    public CurrencyServiceNBPWebAPIImpl(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public BigDecimal getCurrencyRate(String currencyCode) throws Exception {

        return new BigDecimal(retrieveCurrencyRate(currencyCode));
    }

    private String retrieveCurrencyRate(String currencyCode) throws Exception {

        String jsonResponse = callNBPWebAPI();
        JsonNode root = objectMapper.readTree(jsonResponse);

        JsonNode rates = root.get(0).get("rates");

        for (JsonNode rate : rates) {
            String code = rate.path("code").asText();
            if (code.equals(currencyCode)) {
                return rate.path("mid").asText();
            }
        }

        throw new NoSuchCurrencyCodeException("No currency was found for the given code: %s".formatted(currencyCode));
    }

    private String callNBPWebAPI() {
        return restTemplate.getForObject(URI, String.class);
    }
}
