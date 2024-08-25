package com.kodilla.frontend.service;

import com.kodilla.frontend.dto.PatientDto;
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

    public PatientDto save(PatientDto patientDto) {
        if (patientDto.getId() == null) {
            return restTemplate.postForObject(BASE_URL, patientDto, PatientDto.class);
        } else {
            restTemplate.put(BASE_URL + "/" + patientDto.getId(), patientDto);
            return patientDto;
        }
    }

    public void deleteById(Long id) {
        restTemplate.delete(BASE_URL + "/" + id);
    }
}
