package com.ashimCS.linkedinClone.userService.exception;


import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiError {  // ApiError is simply your standard error-response DTO. Instead of every exception returning a different format

    private LocalDateTime timestamp;
    private String error;
    private HttpStatus statusCode;

    public ApiError() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(String error, HttpStatus statusCode) {
        this();  // To avoid duplicating code. Call another constructor of the same class.
        this.error = error;
        this.statusCode = statusCode;
    }

}

/*

Exception = what went wrong inside the application.
ApiError = how we tell the API client about that error.
GlobalExceptionHandler = the bridge between the two.

FLOW:
ApiError(String, HttpStatus)
        ↓
this()
        ↓
ApiError()
        ↓
timestamp = LocalDateTime.now()
        ↓
back to ApiError(String, HttpStatus)
        ↓
error = "Post not found"
statusCode = NOT_FOUND


Why use this()?

To avoid duplicating code.

Without this():

public ApiError(String error, HttpStatus statusCode) {
    this.timestamp = LocalDateTime.now();
    this.error = error;
    this.statusCode = statusCode;
}

You're repeating the timestamp initialization.

With this():

public ApiError(String error, HttpStatus statusCode) {
    this();
    this.error = error;
    this.statusCode = statusCode;
}

Cleaner and reusable.
 */