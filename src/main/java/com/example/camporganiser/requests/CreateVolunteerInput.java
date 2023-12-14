package com.example.camporganiser.requests;

import com.example.camporganiser.entities.GenderEnum;
import com.example.camporganiser.entities.Volunteer;

public record CreateVolunteerInput(String name, Integer age, String email,
                                   GenderEnum gender, Integer phoneNumber) {

    public Volunteer toVolunteer() {
        Volunteer volunteer = new Volunteer();
        volunteer.setName(name);
        volunteer.setEmail(email);
        volunteer.setAge(age);
        volunteer.setGender(gender);
        volunteer.setPhoneNumber(phoneNumber);
        return volunteer;
    }
}
