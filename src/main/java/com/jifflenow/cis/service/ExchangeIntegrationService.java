package com.jifflenow.cis.service;

import microsoft.exchange.webservices.data.core.ExchangeService;

public interface ExchangeIntegrationService {

    ExchangeService getExchangeService();

    ExchangeService getExchangeService(String impersonatedEmailId);
}
