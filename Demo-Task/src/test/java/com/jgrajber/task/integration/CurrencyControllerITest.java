package com.jgrajber.task.integration;

import com.jgrajber.task.dto.CurrencyRateRequestBody;
import com.jgrajber.task.dto.CurrencyRateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CurrencyControllerITest {

    @Autowired
    TestRestTemplate restTemplate;

    @LocalServerPort
    int port;

    String URI_UNDER_TEST;

    @BeforeEach
    void setUp() {
        URI_UNDER_TEST = "http://localhost:%d/currencies/get-current-currency-value-command".formatted(port);
    }

    static final String CURRENCY_CODE_UNDER_TEST = "EUR";
    static final String INCORRECT_CURRENCY_CODE = "XYZ";


    @Test
    void givenCurrencyCode_whenGetCurrentCurrencyValueCommand_thenReturnCurrencyRateAndOK() {

        //given
        CurrencyRateRequestBody requestBody = new CurrencyRateRequestBody(CURRENCY_CODE_UNDER_TEST);

        //when
        ResponseEntity<CurrencyRateResponse> response =
                restTemplate.postForEntity(URI_UNDER_TEST, requestBody, CurrencyRateResponse.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void givenInvalidCurrencyCode_whenGetCurrentCurrencyValueCommand_thenReturnMessageAndBadRequest() {

        //given
        CurrencyRateRequestBody requestBody = new CurrencyRateRequestBody(INCORRECT_CURRENCY_CODE);

        //when
        ResponseEntity<String> response =
                restTemplate.postForEntity(URI_UNDER_TEST, requestBody, String.class);

        //then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
