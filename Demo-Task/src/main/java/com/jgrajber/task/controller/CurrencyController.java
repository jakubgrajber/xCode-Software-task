package com.jgrajber.task.controller;

import com.jgrajber.task.dto.CurrencyRateRequestBody;
import com.jgrajber.task.dto.CurrencyRateResponse;
import com.jgrajber.task.service.CurrencyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("currencies")
public class CurrencyController {

    private CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @PostMapping("get-current-currency-value-command")
    public ResponseEntity getCurrentCurrencyValueCommand(@RequestBody CurrencyRateRequestBody requestBody) {

        try {
            BigDecimal currencyRate = currencyService.getCurrencyRate(requestBody.code().toUpperCase());
            return ResponseEntity.status(HttpStatus.OK).body(new CurrencyRateResponse(currencyRate));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
