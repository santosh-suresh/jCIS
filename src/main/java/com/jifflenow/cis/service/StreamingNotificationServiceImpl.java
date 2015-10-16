package com.jifflenow.cis.service;

import com.jifflenow.cis.listeners.Notifier;
import com.jifflenow.cis.listeners.SubscriptionError;
import com.jifflenow.cis.models.ErrorMessage;
import com.jifflenow.cis.models.SubscriptionRequest;
import com.jifflenow.cis.models.User;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.notification.EventType;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.notification.StreamingSubscription;
import microsoft.exchange.webservices.data.notification.StreamingSubscriptionConnection;
import microsoft.exchange.webservices.data.property.complex.FolderId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Service
public class StreamingNotificationServiceImpl implements StreamingNotificationService {

    private static final Logger logger = LoggerFactory.getLogger(StreamingNotificationServiceImpl.class);

    @Autowired
    private ExchangeIntegrationService integrationService;

    @Override
    public ErrorMessage subscribeToStreamingNotifications(@NotNull SubscriptionRequest request) {

        if(request == null)
            return new ErrorMessage(400, "subscribe will not accept null subscription request");

        List<String> failedUsers = new ArrayList<>();
        for(User user : request.getUsers()) {
            logger.info("Requesting subscription for user with email: " + user.getEmail());
            boolean status = subscribeUserToNotifications(user);
            if(!status) {
                logger.error("Error while requesting subscription for user: " + user.getEmail());
                failedUsers.add(user.getEmail());
            }
        }

        int status = failedUsers.isEmpty() ? 200 : 400;
        String message = failedUsers.isEmpty() ? "Subscribed Users successfully" : "Error while subscription";
        ErrorMessage errorMessage = new ErrorMessage(status, message);
        if(!failedUsers.isEmpty()) {
            for(String user : failedUsers) {
                errorMessage.addErrorMessage("email",user);
            }
        }
        return errorMessage;
    }

    private boolean subscribeUserToNotifications(User user) {
        ExchangeService service = integrationService.getExchangeService(user.getEmail());
        WellKnownFolderName calendar = WellKnownFolderName.Calendar;
        FolderId folderId = new FolderId(calendar);
        List<FolderId> folder = new ArrayList<>();
        folder.add(folderId);

        try {
            StreamingSubscriptionConnection connection = new StreamingSubscriptionConnection(service, 30);
            connection.addOnNotificationEvent(new Notifier(service));
            connection.addOnDisconnect(new SubscriptionError());
            StreamingSubscription subscription = service.subscribeToStreamingNotifications(folder, EventType.FreeBusyChanged);
            connection.addSubscription(subscription);
            connection.open();
        } catch (Exception e) {
            logger.error("Error while subscribing " , e);
            return false;
        }
        return true;
    }
}
