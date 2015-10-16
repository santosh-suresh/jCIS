package com.jifflenow.cis.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionNotification {

    private String uuid;

    private String email;

    @JsonProperty("block_time")
    private List<Meeting> blockTime = new ArrayList<Meeting>();

    private List<Meeting> availability = new ArrayList<Meeting>();

    private List<Meeting> unavailability = new ArrayList<Meeting>();

    public SubscriptionNotification(String uuid, String email) {
        this.uuid = uuid;
        this.email = email;
    }

    public void addBlockedTime(Meeting meeting) {
        blockTime.add(meeting);
    }

    public void addAvailability(Meeting meeting) {
        availability.add(meeting);
    }

    public void addUnavailability(Meeting meeting) {
        unavailability.add(meeting);
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Meeting> getBlockTime() {
        return blockTime;
    }

    public void setBlockTime(List<Meeting> blockTime) {
        this.blockTime = blockTime;
    }

    public List<Meeting> getAvailability() {
        return availability;
    }

    public void setAvailability(List<Meeting> availability) {
        this.availability = availability;
    }

    public List<Meeting> getUnavailability() {
        return unavailability;
    }

    public void setUnavailability(List<Meeting> unavailability) {
        this.unavailability = unavailability;
    }
}
