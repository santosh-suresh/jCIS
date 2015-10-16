package com.jifflenow.cis.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class ErrorMessage {

    private int status;

    private String message;

    private Map<String, String> errors = new HashMap<String, String>();


    public ErrorMessage() {
        super();
    }

    public ErrorMessage(int status, String message) {
        this();
        this.status = status;
        this.message = message;
    }


    public void addErrorMessage(String key, String message) {
        errors.put(key, message);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
