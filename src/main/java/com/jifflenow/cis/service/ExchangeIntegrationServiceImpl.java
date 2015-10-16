package com.jifflenow.cis.service;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ConnectingIdType;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.misc.ImpersonatedUserId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class ExchangeIntegrationServiceImpl implements ExchangeIntegrationService {

    private static final Logger logger = LoggerFactory.getLogger(ExchangeIntegrationServiceImpl.class);

    @Value("${exchange.server.user}")
    private String userName;

    @Value("${exchange.server.password}")
    private String password;

    @Value("${exchange.server.url}")
    private String exchangeUrl;


    public ExchangeService getExchangeService() {
        ExchangeService service = new CustomExchangeService();
        logger.info(String.format("Connecting to server %s as user %s", exchangeUrl, userName));
        ExchangeCredentials credentials = new WebCredentials(userName, password);
        service.setCredentials(credentials);
        try {
            service.setUrl(new URI(exchangeUrl));
        } catch (URISyntaxException e) {
            logger.error("Error while setting up exchange service", e);
            throw new RuntimeException(e);
        }
        return service;
    }

    @Override
    public ExchangeService getExchangeService(String impersonatedEmailId) {
        ExchangeService service = getExchangeService();
        service.setImpersonatedUserId(new ImpersonatedUserId(ConnectingIdType.SmtpAddress, impersonatedEmailId));
        return service;
    }
}
