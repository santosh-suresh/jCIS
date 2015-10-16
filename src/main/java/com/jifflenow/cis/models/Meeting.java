package com.jifflenow.cis.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import microsoft.exchange.webservices.data.core.enumeration.property.LegacyFreeBusyStatus;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.property.complex.MessageBody;

import java.util.Date;

public class Meeting {

    private String subject;

    private String uuid;

    private String status;

    private String body;

    @JsonProperty("start_time")
    private Date startTime;

    @JsonProperty("end_time")
    private Date endTime;

    public Meeting() {
    }

    public Meeting(Appointment appointment) throws Exception {
        this.subject = appointment.getSubject();
        this.uuid = appointment.getICalUid();
        this.startTime = appointment.getStart();
        this.endTime = appointment.getEnd();
        this.body = MessageBody.getStringFromMessageBody(appointment.getBody());
        this.status = appointment.getLegacyFreeBusyStatus() == LegacyFreeBusyStatus.Free ? "unblocked" : "blocked";
    }

    public String getSubject() {
        return subject;
    }

    public String getUuid() {
        return uuid;
    }

    public String getStatus() {
        return status;
    }

    public String getBody() {
        return body;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "subject='" + subject + '\'' +
                ", uuid='" + uuid + '\'' +
                ", status='" + status + '\'' +
                ", body='" + body + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
