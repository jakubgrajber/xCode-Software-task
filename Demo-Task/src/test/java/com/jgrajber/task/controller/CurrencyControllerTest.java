package com.jgrajber.task.controller;

import com.jgrajber.task.exception.NoSuchCurrencyCodeException;
import com.jgrajber.task.service.CurrencyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CurrencyController.class)
class CurrencyControllerTest {

    @MockBean
    CurrencyService currencyService;

    @Autowired
    MockMvc mockMvc;

    static final String URI_UNDER_TEST = "/currencies/get-current-currency-value-command";
    static final String CURRENCY_CODE_UNDER_TEST = "EUR";
    static final String INCORRECT_CURRENCY_CODE = "XYZ";
    static final BigDecimal CURRENT_RATE = new BigDecimal("4.5");


    @Test
    void givenCurrencyCode_whenGetCurrentCurrencyValueCommand_thenReturnCurrencyRateAndOK() throws Exception {

        when(currencyService.getCurrencyRate(CURRENCY_CODE_UNDER_TEST)).thenReturn(CURRENT_RATE);

        mockMvc.
                perform(post(URI_UNDER_TEST).
                        contentType(MediaType.APPLICATION_JSON).
                        content("""
                                {
                                    "code": "%s"
                                }
                                """.formatted(CURRENCY_CODE_UNDER_TEST))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.value", is(CURRENT_RATE.doubleValue())));
    }

    @Test
    void givenInvalidCurrencyCode_whenGetCurrentCurrencyValueCommand_thenReturnMessageAndBadRequest() throws Exception {

        String message = "No currency found";

        when(currencyService.getCurrencyRate(INCORRECT_CURRENCY_CODE)).thenThrow(new NoSuchCurrencyCodeException(message));

        mockMvc.
                perform(post(URI_UNDER_TEST).
                        contentType(MediaType.APPLICATION_JSON).
                        content("""
                                {
                                    "code": "%s"
                                }
                                """.formatted(INCORRECT_CURRENCY_CODE))).
                andExpect(status().isBadRequest())
                .andExpect(content().string(message));
    }

}