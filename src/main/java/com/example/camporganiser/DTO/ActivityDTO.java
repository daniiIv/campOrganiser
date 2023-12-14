package com.example.camporganiser.DTO;

import com.example.camporganiser.entities.ActivityTypeEnum;

import java.util.List;

public record ActivityDTO(Long id, String title, ActivityTypeEnum type, List<VolunteerDTO> volunteers) {
}
