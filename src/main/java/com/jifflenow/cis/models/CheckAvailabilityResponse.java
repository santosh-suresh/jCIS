package com.jifflenow.cis.models;

public class CheckAvailabilityResponse {

    private String email;

    private FreeBusyStatus status;

    public CheckAvailabilityResponse() {
    }

    public CheckAvailabilityResponse(String email, FreeBusyStatus status) {
        this.email = email;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public FreeBusyStatus getStatus() {
        return status;
    }
}
