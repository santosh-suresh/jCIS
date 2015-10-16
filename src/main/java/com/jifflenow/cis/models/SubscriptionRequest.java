package com.jifflenow.cis.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class SubscriptionRequest {

    @JsonProperty("jn_request_id")
    private String requestId;

    @JsonProperty("include_jf_meeting")
    private boolean includeJiffleMeeting;

    @JsonIgnore
    private String callback;

    @JsonProperty("users")
    private List<User> users;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public boolean isIncludeJiffleMeeting() {
        return includeJiffleMeeting;
    }

    public void setIncludeJiffleMeeting(boolean includeJiffleMeeting) {
        this.includeJiffleMeeting = includeJiffleMeeting;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscriptionRequest that = (SubscriptionRequest) o;
        return Objects.equals(includeJiffleMeeting, that.includeJiffleMeeting) &&
                Objects.equals(requestId, that.requestId) &&
                Objects.equals(callback, that.callback) &&
                Objects.equals(users, that.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId, includeJiffleMeeting, callback, users);
    }

    @Override
    public String toString() {
        return "SubscriptionRequest{" +
                "requestId='" + requestId + '\'' +
                ", includeJiffleMeeting=" + includeJiffleMeeting +
                ", callback='" + callback + '\'' +
                ", users=" + users +
                '}';
    }
}
