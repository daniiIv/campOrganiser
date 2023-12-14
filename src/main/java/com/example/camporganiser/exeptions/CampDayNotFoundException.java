package com.example.camporganiser.exeptions;

public class CampDayNotFoundException extends RuntimeException {
    public CampDayNotFoundException(String message) {
        super(message);
    }

    public CampDayNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
