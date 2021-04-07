package com.ooad.secondchance.service;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static com.ooad.secondchance.constants.AppConstants.STATUS;

/**
 * Created by Priyanka on 3/14/21.
 */
@Getter
public class SCBookException extends RuntimeException {
    private final transient Map<String, Object> context;
    private HttpStatus status;

    public SCBookException(String message, Map<String, Object> context) {
        super(message);
        this.context = context;
    }

    public SCBookException(String message, HttpStatus status) {
        super(message);
        this.context = new HashMap<>();
        this.status = status;
        context.put(STATUS, status);
    }
}
