package com.sample.springboot.station.exception;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorInfo {
    private Date timestamp;
    private String message;
    private String details;

    public ErrorInfo(Date timestamp, String message, String details) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }


}