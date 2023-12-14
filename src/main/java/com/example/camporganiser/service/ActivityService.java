package com.example.camporganiser.service;

import com.example.camporganiser.DTO.ActivityDTO;
import com.example.camporganiser.DTO.VolunteerDTO;
import com.example.camporganiser.entities.Activity;
import com.example.camporganiser.entities.CampDay;
import com.example.camporganiser.entities.Volunteer;
import com.example.camporganiser.exeptions.ActivityNotFoundException;
import com.example.camporganiser.exeptions.VolunteerNotFoundException;
import com.example.camporganiser.repositories.ActivityRepository;
import com.example.camporganiser.repositories.VolunteerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final VolunteerRepository volunteerRepository;

    @Autowired
    public ActivityService(ActivityRepository activityRepository, VolunteerRepository volunteerRepository) {
        this.activityRepository = activityRepository;
        this.volunteerRepository = volunteerRepository;
    }


    public Activity createActivity(Activity activity) {
        return activityRepository.save(activity);
    }


    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }


    public Optional<Activity> getActivityById(Long id) {
        return activityRepository.findById(id);
    }

    public ActivityDTO getActivityDetailsDTOById(Long id) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new ActivityNotFoundException("Activity not found with ID: " + id));

        List<VolunteerDTO> volunteerDTOs = activity.getVolunteers().stream()
                .map(volunteer -> new VolunteerDTO(volunteer.getId(), volunteer.getName(), volunteer.getGender(),
                        volunteer.getEmail(), volunteer.getPhoneNumber(), volunteer.getAge()))
                .collect(Collectors.toList());

        return new ActivityDTO(
                activity.getId(),
                activity.getTitle(),
                activity.getType(),
                volunteerDTOs
        );
    }

    public List<ActivityDTO> getAllActivitiesWithVolunteersDTO() {
        List<Activity> activities = activityRepository.findAll();

        return activities.stream()
                .map(this::convertToActivityDetailsDTO)
                .collect(Collectors.toList());
    }

    private ActivityDTO convertToActivityDetailsDTO(Activity activity) {
        List<VolunteerDTO> volunteerDTOs = activity.getVolunteers().stream()
                .map(volunteer -> new VolunteerDTO(volunteer.getId(), volunteer.getName(), volunteer.getGender(),
                        volunteer.getEmail(), volunteer.getPhoneNumber(), volunteer.getAge()))
                .collect(Collectors.toList());

        return new ActivityDTO(
                activity.getId(),
                activity.getTitle(),
                activity.getType(),
                volunteerDTOs
        );
    }

    public Activity updateActivity(Activity updatedActivity) {
        return activityRepository.save(updatedActivity);
    }


    public void deleteActivityById(Long id) {

        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new ActivityNotFoundException("Activity not found with ID: " + id));

        activityRepository.delete(activity);
    }

    public void addVolunteerToActivity(Long activityId, Long volunteerId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new ActivityNotFoundException("Activity not found with ID: " + activityId));

        Volunteer volunteer = volunteerRepository.findById(volunteerId)
                .orElseThrow(() -> new VolunteerNotFoundException("Volunteer not found with ID: " + volunteerId));


        activity.getVolunteers().add(volunteer);
        volunteer.getActivities().add(activity);

        volunteerRepository.save(volunteer);
        activityRepository.save(activity);
    }

    public void deleteVolunteerFromActivity(Long activityId, Long volunteerId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new ActivityNotFoundException("Activity not found with ID: " + activityId));

        Volunteer volunteer = volunteerRepository.findById(volunteerId)
                .orElseThrow(() -> new VolunteerNotFoundException("Volunteer not found with ID: " + volunteerId));

        // Remove the volunteer from the activity
        activity.getVolunteers().remove(volunteer);
        // Remove the activity from the volunteer
        volunteer.getActivities().remove(activity);

        // Save the changes to the database
        activityRepository.save(activity);
        volunteerRepository.save(volunteer);
    }

    public CampDay getCampDayForActivity(Long activityId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new ActivityNotFoundException("Activity not found with ID: " + activityId));
        return activity.getCampDay();
    }

    public Set<Volunteer> getVolunteersForActivity(Long activityId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new ActivityNotFoundException("Activity not found with ID: " + activityId));
        return activity.getVolunteers();
    }
    

}
