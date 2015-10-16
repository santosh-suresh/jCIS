package com.jifflenow.cis;

import com.jifflenow.cis.models.CheckAvailabilityRequest;
import com.jifflenow.cis.models.CheckAvailabilityResponse;
import com.jifflenow.cis.models.ErrorMessage;
import com.jifflenow.cis.models.SubscriptionRequest;
import com.jifflenow.cis.service.CheckAvailability;
import com.jifflenow.cis.service.StreamingNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@RestController
public class BrokerController {

//    private static final Logger logger = LoggerFactory.getLogger(BrokerController.class);

    @Autowired
    private StreamingNotificationService notificationService;

    @Autowired
    private CheckAvailability checkAvailability;

    @RequestMapping(value = "/users/subscribe", method = RequestMethod.POST)
    public ErrorMessage subscribeToStreamingNotifications(@RequestBody SubscriptionRequest request) {
        return notificationService.subscribeToStreamingNotifications(request);
    }


    @RequestMapping(value = "/users/check_availability", method = RequestMethod.POST)
    public List<CheckAvailabilityResponse> getAvailability(@RequestBody CheckAvailabilityRequest request) throws Exception {
        List<CheckAvailabilityResponse> responses = new ArrayList<>();
        List<Future<CheckAvailabilityResponse>> futures = new ArrayList<>();
        for(String email : request.getEmailAddress()) {
            Future<CheckAvailabilityResponse> availability = checkAvailability.getAvailability(email, request.getStartDate(), request.getEndDate());
            futures.add(availability);
        }

        for(Future<CheckAvailabilityResponse> f : futures) {
            CheckAvailabilityResponse resp = f.get();
            responses.add(resp);
        }
        return responses;
    }

}
