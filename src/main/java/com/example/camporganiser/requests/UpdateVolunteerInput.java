package com.example.camporganiser.requests;

import com.example.camporganiser.entities.GenderEnum;

public record UpdateVolunteerInput(String name,
                                   GenderEnum gender,
                                   String email,
                                   Integer phoneNumber,
                                   int age) {
}
