package com.trivago.hotels.application.config;

import com.trivago.hotels.domain.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(HotelNotFoundException.class)
    public ProblemDetail handleHotelNotFoundException(HotelNotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle(e.getClass().getCanonicalName());
        problemDetail.setDetail("Hotel not found with id " + e.getId());
        return problemDetail;
    }


    @ExceptionHandler(SubscriptionNotFoundException.class)
    public ProblemDetail handleSubscriptionNotFoundException(SubscriptionNotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle(e.getClass().getCanonicalName());
        problemDetail.setDetail("Subscription not found with id " + e.getId());
        return problemDetail;
    }

    @ExceptionHandler(HotelAlreadyExistException.class)
    public ProblemDetail handleHotelAlreadyExistException(HotelAlreadyExistException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle(e.getClass().getCanonicalName());
        problemDetail.setDetail("Hotel with name " + e.getName() + " already exists");
        return problemDetail;
    }

    @ExceptionHandler(InvalidSubscriptionStatusException.class)
    public ProblemDetail handleInvalidSubscriptionStatusException(InvalidSubscriptionStatusException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle(e.getClass().getCanonicalName());
        problemDetail.setDetail(
                "Subscription with id " + e.getId() + " should be in " + e.getStatus() + " status to perform this action"
        );
        return problemDetail;
    }

    @ExceptionHandler(ActiveSubscriptionExistException.class)
    public ProblemDetail handleActiveSubscriptionExistException(ActiveSubscriptionExistException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle(e.getClass().getCanonicalName());
        problemDetail.setDetail(
                "There is an active subscription for hotel with id " + e.getHotelId()
        );
        return problemDetail;
    }
}
