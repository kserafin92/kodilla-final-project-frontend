package com.kodilla.frontend.service;

import com.kodilla.frontend.domain.PatientDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class PatientService {

    private final RestTemplate restTemplate;
    private static final String BASE_URL = "http://localhost:8081/patients";

    public PatientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<PatientDto> findAll() {
        return Arrays.asList(restTemplate.getForObject(BASE_URL, PatientDto[].class));
    }

    public PatientDto findById(Long id) {
        return restTemplate.getForObject(BASE_URL + "/" + id, PatientDto.class);
    }

    public PatientDto save(PatientDto patient) {
        if (patient.getId() == null) {
            return restTemplate.postForObject(BASE_URL, patient, PatientDto.class);
        } else {
            restTemplate.put(BASE_URL + "/" + patient.getId(), patient);
            return patient;
        }
    }

    public void deleteById(Long id) {
        restTemplate.delete(BASE_URL + "/" + id);
    }
}
