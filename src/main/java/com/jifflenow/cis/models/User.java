package com.jifflenow.cis.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class User {

    @JsonProperty("email")
    private String email;

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("user_schedule_url:")
    private String userScheduleUrl;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserScheduleUrl() {
        return userScheduleUrl;
    }

    public void setUserScheduleUrl(String userScheduleUrl) {
        this.userScheduleUrl = userScheduleUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) &&
                Objects.equals(uuid, user.uuid) &&
                Objects.equals(userScheduleUrl, user.userScheduleUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, uuid, userScheduleUrl);
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", uuid='" + uuid + '\'' +
                ", userScheduleUrl='" + userScheduleUrl + '\'' +
                '}';
    }
}
