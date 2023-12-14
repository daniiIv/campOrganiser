package com.example.camporganiser.DTO;

import com.example.camporganiser.entities.GenderEnum;

public record VolunteerDTO(Long id, String name, GenderEnum gender, String email, Integer phoneNumber, int age) {
    @Override
    public Long id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public GenderEnum gender() {
        return gender;
    }

    @Override
    public String email() {
        return email;
    }

    @Override
    public Integer phoneNumber() {
        return phoneNumber;
    }

    @Override
    public int age() {
        return age;
    }
}
