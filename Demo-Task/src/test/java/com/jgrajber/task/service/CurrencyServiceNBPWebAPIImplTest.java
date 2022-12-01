package com.jgrajber.task.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jgrajber.task.exception.NoSuchCurrencyCodeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceNBPWebAPIImplTest {

    @Mock
    RestTemplate restTemplate;

    ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    CurrencyServiceNBPWebAPIImpl currencyService = new CurrencyServiceNBPWebAPIImpl(restTemplate, objectMapper);

    static final String URI = "http://api.nbp.pl/api/exchangerates/tables/A?format=json";

    static final String CURRENCY_CODE_UNDER_TEST = "EUR";
    static final String INCORRECT_CURRENCY_CODE = "XYZ";
    static final BigDecimal CURRENT_RATE = new BigDecimal("4.5");

    static final String NBP_API_RESPONSE = """
            [
                {
                    "rates": [
                        {
                            "code": "%s",
                            "mid": %s
                        }
                    ]
                }
            ]
            """.formatted(CURRENCY_CODE_UNDER_TEST, CURRENT_RATE.toString());

    @Test
    void givenCurrencyCode_whenGetCurrencyRate_thenReturnCurrencyRate() throws Exception {

        when(restTemplate.getForObject(URI, String.class)).thenReturn(NBP_API_RESPONSE);

        BigDecimal rate = currencyService.getCurrencyRate(CURRENCY_CODE_UNDER_TEST);

        Assertions.assertEquals(CURRENT_RATE, rate);
    }

    @Test
    void givenIncorrectCurrencyCode_whenGetCurrencyRate_thenThrowNoSuchCurrencyCodeException() {

        when(restTemplate.getForObject(URI, String.class)).thenReturn(NBP_API_RESPONSE);

        Assertions.assertThrows(NoSuchCurrencyCodeException.class,
                () -> currencyService.getCurrencyRate(INCORRECT_CURRENCY_CODE));

    }
}