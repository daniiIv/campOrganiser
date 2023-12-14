package com.example.camporganiser.controllers;

import com.example.camporganiser.entities.Activity;
import com.example.camporganiser.entities.CampDay;
import com.example.camporganiser.exeptions.CampDayNotFoundException;
import com.example.camporganiser.requests.CreateCampDayInput;
import com.example.camporganiser.requests.UpdateCampDayInput;
import com.example.camporganiser.service.CampDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/camp-days")
@CrossOrigin(origins = "http://localhost:3000")
public class CampDayController {
    private final CampDayService campDayService;

    @Autowired
    public CampDayController(CampDayService campDayService) {
        this.campDayService = campDayService;
    }

    @PostMapping("/create")
    public ResponseEntity<CampDay> createCampDay(@RequestBody CreateCampDayInput campDay) {
        CampDay createdCampDay = campDayService.createCampDay(campDay.toCampDay());
        return new ResponseEntity<>(createdCampDay, HttpStatus.CREATED);
    }

    @PostMapping("/{campDayId}/add-existing-activity/{activityId}")
    public ResponseEntity<CampDay> addExistingActivityToCampDay(
            @PathVariable Long campDayId,
            @PathVariable Long activityId) {
        CampDay updatedCampDay = campDayService.addExistingActivityToCampDay(campDayId, activityId);
        return ResponseEntity.ok(updatedCampDay);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampDay> getCampDayById(@PathVariable Long id) {
        Optional<CampDay> campDayOptional = campDayService.getCampDayById(id);

        return campDayOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<CampDay>> getAllCampDays() {
        List<CampDay> campDays = campDayService.getAllCampDays();

        if (campDays.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(campDays);
        }
    }

    @GetMapping("/{campDayId}/activities")
    public ResponseEntity<List<Activity>> getActivitiesForCampDay(@PathVariable Long campDayId) {
        List<Activity> activities = campDayService.getActivitiesForCampDay(campDayId);
        return ResponseEntity.ok(activities);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CampDay> updateCampDay(@PathVariable Long id, @RequestBody UpdateCampDayInput updateCampDayInput) {
        Optional<CampDay> campDay = campDayService.getCampDayById(id);
        CampDay campDayToUpdate = campDay.get();
        campDayToUpdate.setDate(updateCampDayInput.date());
        CampDay updatedCampDay = campDayService.updateCampDay(campDayToUpdate);
        return ResponseEntity.ok(updatedCampDay);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampDay(@PathVariable Long id) {
        try {
            campDayService.deleteCampDayById(id);
            return ResponseEntity.noContent().build();
        } catch (CampDayNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{campDayId}/remove-existing-activity/{activityId}")
    public ResponseEntity<CampDay> removeExistingActivityFromCampDay(
            @PathVariable Long campDayId,
            @PathVariable Long activityId) {
        CampDay updatedCampDay = campDayService.removeExistingActivityFromCampDay(campDayId, activityId);
        return ResponseEntity.ok(updatedCampDay);
    }
}
