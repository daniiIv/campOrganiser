package com.example.camporganiser.requests;

import com.example.camporganiser.entities.ActivityTypeEnum;

public record UpdateActivityInput(String title,
                                  ActivityTypeEnum type,
                                  String description) {
}
