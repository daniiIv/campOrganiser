package com.example.camporganiser.exeptions;

public class VolunteerNotFoundException extends RuntimeException{
    public VolunteerNotFoundException(String message) {
        super(message);
    }
}
