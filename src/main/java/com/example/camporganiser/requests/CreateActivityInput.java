package com.example.camporganiser.requests;

import com.example.camporganiser.entities.Activity;
import com.example.camporganiser.entities.ActivityTypeEnum;

public record CreateActivityInput(ActivityTypeEnum typeEnum,
                                  String title,
                                  String description) {

    public Activity toActivity() {
        Activity activity = new Activity();
        activity.setDescription(description());
        activity.setTitle(title);
        activity.setType(typeEnum);

        return activity;

    }
}
