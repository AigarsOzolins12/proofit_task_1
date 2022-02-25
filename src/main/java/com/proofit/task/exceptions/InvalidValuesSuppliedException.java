package com.proofit.task.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidValuesSuppliedException extends RuntimeException {
    public InvalidValuesSuppliedException(String message) {
        super(message);
    }
}
