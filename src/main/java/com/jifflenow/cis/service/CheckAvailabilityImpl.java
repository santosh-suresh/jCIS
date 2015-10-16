package com.jifflenow.cis.service;

import com.jifflenow.cis.models.CheckAvailabilityRequest;
import com.jifflenow.cis.models.CheckAvailabilityResponse;
import com.jifflenow.cis.models.FreeBusyStatus;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.service.folder.CalendarFolder;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.search.CalendarView;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.Future;

@Service
public class CheckAvailabilityImpl implements CheckAvailability {

    private static final Logger logger = LoggerFactory.getLogger(CheckAvailabilityImpl.class);

    private final Object lock = new Object();

    @Autowired
    private ExchangeIntegrationService integrationService;

    @Override
    public List<CheckAvailabilityResponse> getAvailabilityFor(CheckAvailabilityRequest request) {
        CalendarView view = new CalendarView(GregorianCalendar.from(request.getStartDate()).getTime(), GregorianCalendar.from(request.getEndDate()).getTime(), 10);
        List<CheckAvailabilityResponse> result = new ArrayList<>();
        CalendarFolder folder = null;
        for(String email : request.getEmailAddress()) {
            logger.info(String.format("Fetch Calendar availability for: %s", email));

            try (ExchangeService service = integrationService.getExchangeService(email)) {
                folder = CalendarFolder.bind(service, WellKnownFolderName.Calendar);
                FindItemsResults<Appointment> appointments = folder.findAppointments(view);
                int appointmentCount = appointments.getItems().size();
                logger.info(String.format("Found %d appointments for %s", appointmentCount, email));
                FreeBusyStatus status = appointmentCount > 0 ? FreeBusyStatus.Blocked : FreeBusyStatus.Available;
                CheckAvailabilityResponse response = new CheckAvailabilityResponse(email, status);
                result.add(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    @Async
    public Future<CheckAvailabilityResponse> getAvailability(String email, ZonedDateTime startDate, ZonedDateTime endDate) throws InterruptedException {
        CalendarFolder folder = null;
        try (ExchangeService service = integrationService.getExchangeService(email)) {

            synchronized (lock) {
                folder = CalendarFolder.bind(service, WellKnownFolderName.Calendar);
            }

            CalendarView view = new CalendarView(GregorianCalendar.from(startDate).getTime(), GregorianCalendar.from(endDate).getTime(), 10);
            FindItemsResults<Appointment> appointments = folder.findAppointments(view);
            int appointmentCount = appointments.getItems().size();
            logger.info(String.format("Found %d appointments for %s", appointmentCount, email));
            FreeBusyStatus status = appointmentCount > 0 ? FreeBusyStatus.Blocked : FreeBusyStatus.Available;
            CheckAvailabilityResponse response = new CheckAvailabilityResponse(email, status);
            return new AsyncResult<>(response);
        } catch (Exception e) {
            throw new RuntimeException("Error connecting to calendar folder", e);
        }


    }
}
