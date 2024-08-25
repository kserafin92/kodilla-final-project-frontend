package com.kodilla.frontend.service;


import com.kodilla.frontend.domain.Doctor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class DoctorService {

    private final RestTemplate restTemplate;
    private static final String BASE_URL = "http://localhost:8081/doctors";

    public DoctorService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Doctor> findAll() {
        return List.of(restTemplate.getForObject(BASE_URL, Doctor[].class));
    }

    public Doctor save(Doctor doctor) {
        if (doctor.getId() == null) {
            return restTemplate.postForObject(BASE_URL, doctor, Doctor.class);
        } else {
            restTemplate.put(BASE_URL + "/" + doctor.getId(), doctor);
            return doctor;
        }
    }

    public void deleteById(Long id) {
        restTemplate.delete(BASE_URL + "/" + id);
    }
}