package com.jifflenow.cis.listeners;

import com.jifflenow.cis.models.Meeting;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.property.BasePropertySet;
import microsoft.exchange.webservices.data.core.response.GetItemResponse;
import microsoft.exchange.webservices.data.core.response.ServiceResponseCollection;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.notification.ItemEvent;
import microsoft.exchange.webservices.data.notification.NotificationEvent;
import microsoft.exchange.webservices.data.notification.NotificationEventArgs;
import microsoft.exchange.webservices.data.notification.StreamingSubscriptionConnection;
import microsoft.exchange.webservices.data.property.complex.ItemId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Notifier implements StreamingSubscriptionConnection.INotificationEventDelegate {

    private static final Logger logger = LoggerFactory.getLogger(Notifier.class);

    private final ExchangeService service;

    public Notifier(ExchangeService service) {
        this.service = service;
    }

    @Override
    public void notificationEventDelegate(Object sender, NotificationEventArgs args) {
        logger.info("Received notification for " + service.getImpersonatedUserId().getId());
        Iterable<NotificationEvent> events = args.getEvents();
        List<ItemId> ids = new ArrayList<>();
        for (NotificationEvent e: events) {
            ItemEvent event = (ItemEvent) e;
            ids.add(event.getItemId());
            logger.debug(String.format("Got event with id: %s", event.getItemId().toString()));
        }

        if(!ids.isEmpty()) {
            try {
                ServiceResponseCollection<GetItemResponse> responses = service.bindToItems(ids, new PropertySet(BasePropertySet.FirstClassProperties));
                logger.info(String.format("Received %d responses from notification", responses.getCount()));
                if(responses.getCount() > 0) {
                    for (GetItemResponse response : responses) {
                        Appointment appointment = (Appointment) response.getItem();
                        if (appointment != null) {
                            Meeting meeting = new Meeting(appointment);
                            logger.info(meeting.toString());
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }
}
