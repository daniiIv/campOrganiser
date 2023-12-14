package com.example.camporganiser.controllers;


import com.example.camporganiser.DTO.VolunteerDTO;
import com.example.camporganiser.entities.Activity;
import com.example.camporganiser.entities.Volunteer;
import com.example.camporganiser.exeptions.VolunteerNotFoundException;
import com.example.camporganiser.requests.CreateVolunteerInput;
import com.example.camporganiser.requests.UpdateVolunteerInput;
import com.example.camporganiser.service.VolunteerService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/volunteers")
@CrossOrigin(origins = "http://localhost:3000")
public class VolunteerController {

    private final VolunteerService volunteerService;


    @Autowired
    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @PostMapping("/create")
    public ResponseEntity<Volunteer> createVolunteer(@RequestBody CreateVolunteerInput createVolunteerInput) {
        Volunteer createdVolunteer = volunteerService.createVolunteer(createVolunteerInput.toVolunteer());
        return new ResponseEntity<>(createdVolunteer, HttpStatus.CREATED);


    }

    @GetMapping("/{id}")
    public ResponseEntity<Volunteer> getVolunteerById(@PathVariable Long id) {
        Optional<Volunteer> volunteerOptional = volunteerService.getVolunteerById(id);

        return volunteerOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Volunteer>> getAllVolunteers() {
        List<Volunteer> volunteers = volunteerService.getAllVolunteers();

        if (volunteers.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(volunteers);
        }
    }

    @GetMapping("/allDTO")
    public ResponseEntity<List<VolunteerDTO>> getAllVolunteersDTO() {
        List<VolunteerDTO> volunteerDTOs = volunteerService.getAllVolunteersDTO();

        if (volunteerDTOs.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(volunteerDTOs);
        }
    }

    @GetMapping("/{id}/dto")
    public ResponseEntity<VolunteerDTO> getVolunteerDTOById(@PathVariable Long id) {
        VolunteerDTO volunteerDTO = volunteerService.getVolunteerDTOById(id);
        return ResponseEntity.ok(volunteerDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Volunteer> updateVolunteer(@PathVariable Long id, @RequestBody UpdateVolunteerInput updatedVolunteerInput) {
        Optional<Volunteer> volunteer = volunteerService.getVolunteerById(id);
        if (volunteer.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Volunteer volunteerToUpdate = volunteer.get();
        volunteerToUpdate.setPhoneNumber(updatedVolunteerInput.phoneNumber());
        volunteerToUpdate.setAge(updatedVolunteerInput.age());
        volunteerToUpdate.setGender(updatedVolunteerInput.gender());
        volunteerToUpdate.setEmail(updatedVolunteerInput.email());
        volunteerToUpdate.setName(updatedVolunteerInput.name());
        Volunteer volunteerUpdated = volunteerService.updateVolunteer(volunteerToUpdate);
        return ResponseEntity.ok(volunteerUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVolunteer(@PathVariable Long id) {
        try {
            volunteerService.deleteVolunteerById(id);
            return ResponseEntity.noContent().build();
        } catch (VolunteerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


}
