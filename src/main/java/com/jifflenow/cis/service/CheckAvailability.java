package com.jifflenow.cis.service;

import com.jifflenow.cis.models.CheckAvailabilityRequest;
import com.jifflenow.cis.models.CheckAvailabilityResponse;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.Future;

public interface CheckAvailability {

    List<CheckAvailabilityResponse> getAvailabilityFor(CheckAvailabilityRequest request);

    Future<CheckAvailabilityResponse> getAvailability(String email, ZonedDateTime startDate, ZonedDateTime endDate) throws InterruptedException;
}
