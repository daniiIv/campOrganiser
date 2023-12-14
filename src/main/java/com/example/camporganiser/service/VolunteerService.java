package com.example.camporganiser.service;

import com.example.camporganiser.DTO.VolunteerDTO;
import com.example.camporganiser.entities.Activity;
import com.example.camporganiser.entities.Volunteer;
import com.example.camporganiser.exeptions.VolunteerNotFoundException;
import com.example.camporganiser.repositories.VolunteerRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VolunteerService {
    private final VolunteerRepository volunteerRepository;

    @Autowired
    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    public Volunteer createVolunteer(Volunteer newVolunteer) {
        return volunteerRepository.save(newVolunteer);
    }

    public List<Volunteer> getAllVolunteers() {
        return volunteerRepository.findAll();
    }

    public Optional<Volunteer> getVolunteerById(Long id) {
        return volunteerRepository.findById(id);
    }

    public List<VolunteerDTO> getAllVolunteersDTO() {
        List<Volunteer> volunteers = volunteerRepository.findAll();
        return volunteers.stream()
                .map(volunteer -> new VolunteerDTO(volunteer.getId(), volunteer.getName(), volunteer.getGender(),
                        volunteer.getEmail(), volunteer.getPhoneNumber(), volunteer.getAge()))
                .collect(Collectors.toList());
    }

    public VolunteerDTO getVolunteerDTOById(Long id) {
        Volunteer volunteer = volunteerRepository.findById(id)
                .orElseThrow(() -> new VolunteerNotFoundException("Volunteer not found with ID: " + id));

        return convertToDTO(volunteer);
    }

    private VolunteerDTO convertToDTO(Volunteer volunteer) {
        return new VolunteerDTO(volunteer.getId(), volunteer.getName(), volunteer.getGender(),
                volunteer.getEmail(), volunteer.getPhoneNumber(), volunteer.getAge());
    }

    public void deleteVolunteerById(Long id) {
        volunteerRepository.deleteById(id);
    }

    public Volunteer updateVolunteer(Volunteer updatedVolunteer) {
        return volunteerRepository.save(updatedVolunteer);
    }


}
