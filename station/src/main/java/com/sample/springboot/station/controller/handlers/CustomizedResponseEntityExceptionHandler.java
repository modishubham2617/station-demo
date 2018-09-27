package com.sample.springboot.station.controller.handlers;

import com.sample.springboot.station.exception.ErrorInfo;
import com.sample.springboot.station.exception.StationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(StationNotFoundException.class)
    public final ResponseEntity<ErrorInfo> handleUserNotFoundException(StationNotFoundException ex, WebRequest request) {
        ErrorInfo info = new ErrorInfo(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorInfo> handleAllExceptions(Exception ex, WebRequest request) {
        ErrorInfo info = new ErrorInfo(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
