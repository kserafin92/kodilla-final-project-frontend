package com.kodilla.frontend.service;

import com.kodilla.frontend.domain.DoctorDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    private final RestTemplate restTemplate;
    private static final String BASE_URL = "http://localhost:8081/doctors";

    public DoctorService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Collection<DoctorDto> findAll() {
        return Arrays.stream(restTemplate.getForObject(BASE_URL, DoctorDto[].class))
                .collect(Collectors.toList());
    }

    public DoctorDto findById(Long id) {
        return restTemplate.getForObject(BASE_URL + "/" + id, DoctorDto.class);
    }

    public DoctorDto save(DoctorDto doctor) {
        if (doctor.getId() == null) {
            return restTemplate.postForObject(BASE_URL, doctor, DoctorDto.class);
        } else {
            restTemplate.put(BASE_URL + "/" + doctor.getId(), doctor);
            return doctor;
        }
    }

    public void deleteById(Long id) {
        restTemplate.delete(BASE_URL + "/" + id);
    }
}
