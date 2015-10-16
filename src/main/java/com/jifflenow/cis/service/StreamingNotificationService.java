package com.jifflenow.cis.service;

import com.jifflenow.cis.models.ErrorMessage;
import com.jifflenow.cis.models.SubscriptionRequest;

public interface StreamingNotificationService {

    ErrorMessage subscribeToStreamingNotifications(SubscriptionRequest request);

}
