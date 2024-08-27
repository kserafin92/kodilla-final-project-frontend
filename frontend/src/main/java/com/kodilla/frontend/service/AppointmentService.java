package com.kodilla.frontend.service;

import com.kodilla.frontend.domain.dto.AppointmentDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AppointmentService {
    private static final String BASE_URL = "http://localhost:8081/appointments";
    private final RestTemplate restTemplate;
    public AppointmentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public List<AppointmentDto> findAll() {
        return List.of(restTemplate.getForObject(BASE_URL, AppointmentDto[].class));
    }
    public AppointmentDto save(AppointmentDto appointment) {
        if (appointment.getId() == null) {
            return restTemplate.postForObject(BASE_URL, appointment, AppointmentDto.class);
        } else {
            restTemplate.put(BASE_URL + "/" + appointment.getId(), appointment);
            return appointment;
        }
    }
    public void deleteById(Long id) {
        restTemplate.delete(BASE_URL + "/" + id);
    }
}
