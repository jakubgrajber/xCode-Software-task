package com.jgrajber.task.service;

import java.math.BigDecimal;

public interface CurrencyService {

    BigDecimal getCurrencyRate(String currencyCode) throws Exception;
}
