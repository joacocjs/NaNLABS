package com.nanlabs.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

public class ApiErrorResponse {
    private final String timestamp = Instant.now().toString();
    private final HttpStatus httpStatus;
    private final int errorCode;
    private final String title;
    private final String message;
    private final Object details;

    public ApiErrorResponse(HttpStatus httpStatus, int errorCode, String title, String message, Object details) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.title = title;
        this.message = message;
        this.details = details;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getTitle() {
        return this.title;
    }

    public String getMessage() {
        return this.message;
    }

    public Object getDetails() {
        return this.details;
    }


    public static final class Entity {
        private Entity() {
            throw new IllegalStateException("Utility class");
        }

        public static ResponseEntity<ApiErrorResponse> of(HttpStatus httpStatus, int errorCode, String title, String message, Object details) {
            return new ResponseEntity(new ApiErrorResponse(httpStatus, errorCode, title, message, details), httpStatus);
        }

        public static ResponseEntity<ApiErrorResponse> of(HttpStatus httpStatus, int errorCode, String title, String message) {
            return of(httpStatus, errorCode, title, message, (Object)null);
        }
    }
}