package com.example.camporganiser.service;

import com.example.camporganiser.entities.Activity;
import com.example.camporganiser.entities.CampDay;
import com.example.camporganiser.exeptions.ActivityNotFoundException;
import com.example.camporganiser.exeptions.CampDayNotFoundException;
import com.example.camporganiser.repositories.ActivityRepository;
import com.example.camporganiser.repositories.CampDayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CampDayService {
    private final CampDayRepository campDayRepository;
    private final ActivityRepository activityRepository;

    @Autowired
    public CampDayService(CampDayRepository campDayRepository, ActivityRepository activityRepository) {
        this.campDayRepository = campDayRepository;
        this.activityRepository = activityRepository;
    }

    public CampDay createCampDay(CampDay campDay) {
        return campDayRepository.save(campDay);
    }


    public List<CampDay> getAllCampDays() {
        return campDayRepository.findAll();
    }

    public Optional<CampDay> getCampDayById(Long id) {
        return campDayRepository.findById(id);
    }

    public List<Activity> getActivitiesForCampDay(Long campDayId) {
        CampDay campDay = campDayRepository.findById(campDayId)
                .orElseThrow(() -> new CampDayNotFoundException("CampDay not found with ID: " + campDayId));

        return campDay.getActivities();
    }

    public CampDay updateCampDay(CampDay updatedCampDay) {
        return campDayRepository.save(updatedCampDay);
    }

    public void deleteCampDayById(Long campId) {
        CampDay campDay = campDayRepository.findById(campId).orElse(null);
        if (campDay != null) {
            campDay.getActivities().clear();
            campDay.setActivities(null);
            campDayRepository.save(campDay);
        }

        campDayRepository.deleteById(campId);
        campDayRepository.deleteById(campId);
    }


    public CampDay addExistingActivityToCampDay(Long campDayId, Long activityId) {
        CampDay campDay = campDayRepository.findById(campDayId)
                .orElseThrow(() -> new CampDayNotFoundException("Camp day not found with id: " + campDayId));
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new ActivityNotFoundException("Activity not found with ID: " + activityId));

        campDay.getActivities().add(activity);
        activity.setCampDay(campDay);
        return campDayRepository.save(campDay);
    }

    public CampDay removeExistingActivityFromCampDay(Long campDayId, Long activityId) {
        CampDay campDay = campDayRepository.findById(campDayId)
                .orElseThrow(() -> new CampDayNotFoundException("Camp day not found with ID: " + campDayId));

        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new ActivityNotFoundException("Activity not found with ID: " + activityId));

        campDay.getActivities().remove(activity);
        activity.setCampDay(null);

        campDayRepository.save(campDay);
        activityRepository.save(activity);

        return campDay;
    }

    public void deleteCampDay(Long campDayId) {
        campDayRepository.deleteById(campDayId);
    }

}
