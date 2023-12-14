package com.example.camporganiser.requests;

import com.example.camporganiser.entities.CampDay;

import javax.xml.crypto.Data;
import java.time.LocalDate;

public record CreateCampDayInput(LocalDate date) {
    public CampDay toCampDay() {
        CampDay campDay = new CampDay();
        campDay.setDate(date);
        return campDay;
    }
}
