package com.jifflenow.cis.listeners;

import microsoft.exchange.webservices.data.notification.StreamingSubscriptionConnection;
import microsoft.exchange.webservices.data.notification.SubscriptionErrorEventArgs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubscriptionError implements StreamingSubscriptionConnection.ISubscriptionErrorDelegate {

    private static final Logger logger = LoggerFactory.getLogger(SubscriptionError.class);

    @Override
    public void subscriptionErrorDelegate(Object sender, SubscriptionErrorEventArgs args) {
        logger.info("Got a disconnection request");
        StreamingSubscriptionConnection connection = (StreamingSubscriptionConnection) sender;
        try {
            if (!connection.getIsOpen())
                connection.open();
        } catch (Exception e) {
            logger.error("Error while trying to open a connection.", e);
            throw new RuntimeException("Error while reopening", e);
        }
    }
}

