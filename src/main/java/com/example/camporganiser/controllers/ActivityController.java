package com.example.camporganiser.controllers;

import com.example.camporganiser.DTO.ActivityDTO;
import com.example.camporganiser.entities.Activity;
import com.example.camporganiser.entities.CampDay;
import com.example.camporganiser.entities.Volunteer;
import com.example.camporganiser.exeptions.ActivityNotFoundException;
import com.example.camporganiser.exeptions.VolunteerNotFoundException;
import com.example.camporganiser.requests.CreateActivityInput;
import com.example.camporganiser.requests.UpdateActivityInput;
import com.example.camporganiser.service.ActivityService;
import com.example.camporganiser.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/activities")
@CrossOrigin(origins = "http://localhost:3000")
public class ActivityController {
    private final ActivityService activityService;


    @Autowired
    public ActivityController(ActivityService activityService, VolunteerService volunteerService) {
        this.activityService = activityService;

    }

    @PostMapping("/create")
    public ResponseEntity<Activity> createActivity(@RequestBody CreateActivityInput activity) {
        Activity createdActivity = activityService.createActivity(activity.toActivity());
        return new ResponseEntity<>(createdActivity, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Activity> getActivityById(@PathVariable Long id) {
        Optional<Activity> activityOptional = activityService.getActivityById(id);

        return activityOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Activity>> getAllActivities() {
        List<Activity> activities = activityService.getAllActivities();

        if (activities.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(activities);
        }
    }

    //Getting activity with volunteersDTO
    @GetMapping("/{id}/DTO")
    public ResponseEntity<ActivityDTO> getActivityDetailsDTOById(@PathVariable Long id) {
        ActivityDTO activityDTO = activityService.getActivityDetailsDTOById(id);

        return ResponseEntity.ok(activityDTO);
    }

    @GetMapping("/allDTO")
    public ResponseEntity<List<ActivityDTO>> getAllActivitiesWithVolunteersDTO() {
        List<ActivityDTO> activityDetailsDTOs = activityService.getAllActivitiesWithVolunteersDTO();

        if (activityDetailsDTOs.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(activityDetailsDTOs);
        }
    }

    @GetMapping("/{activityId}/camp-day")
    public ResponseEntity<CampDay> getCampDayForActivity(@PathVariable Long activityId) {
        CampDay campDay = activityService.getCampDayForActivity(activityId);
        return ResponseEntity.ok(campDay);
    }

    @GetMapping("/{activityId}/volunteers")
    public ResponseEntity<Set<Volunteer>> getVolunteersForActivity(@PathVariable Long activityId) {
        Set<Volunteer> volunteers = activityService.getVolunteersForActivity(activityId);
        return ResponseEntity.ok(volunteers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Activity> updateActivity(@PathVariable Long id, @RequestBody UpdateActivityInput updateActivityInput) {
        Optional<Activity> activity = activityService.getActivityById(id);
        if (activity.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Activity activityToUpdate = activity.get();
        activityToUpdate.setType(updateActivityInput.type());
        activityToUpdate.setDescription(updateActivityInput.description());
        activityToUpdate.setTitle(updateActivityInput.title());

        Activity activityUpdated = activityService.updateActivity(activityToUpdate);
        return new ResponseEntity<>(activityUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        try {
            activityService.deleteActivityById(id);
            return ResponseEntity.noContent().build();
        } catch (ActivityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{activityId}/add-volunteer/{volunteerId}")
    public ResponseEntity<Void> addVolunteerToActivity(
            @PathVariable Long activityId,
            @PathVariable Long volunteerId
    ) {
        activityService.addVolunteerToActivity(activityId, volunteerId);

        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/remove-volunteer/{activityId}/{volunteerId}")
    public ResponseEntity<?> removeVolunteerFromActivity(
            @PathVariable Long activityId,
            @PathVariable Long volunteerId) {

        try {
            activityService.deleteVolunteerFromActivity(activityId, volunteerId);
            return ResponseEntity.ok().build();
        } catch (ActivityNotFoundException | VolunteerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
