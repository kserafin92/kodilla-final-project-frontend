package com.kodilla.frontend.service;

import com.kodilla.frontend.domain.Patient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

@Service
public class PatientService {

    private final WebClient webClient;

    public PatientService() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/patients")
                .build();
    }

    public List<Patient> getAllPatients() {
        return webClient.get()
                .retrieve()
                .bodyToFlux(Patient.class)
                .collectList()
                .block();
    }

    public Patient getPatientById(Long id) {
        return webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(Patient.class)
                .block();
    }

    public Patient createPatient(Patient patient) {
        return webClient.post()
                .bodyValue(patient)
                .retrieve()
                .bodyToMono(Patient.class)
                .block();
    }

    public Patient updatePatient(Long id, Patient patient) {
        return webClient.put()
                .uri("/{id}", id)
                .bodyValue(patient)
                .retrieve()
                .bodyToMono(Patient.class)
                .block();
    }

    public void deletePatient(Long id) {
        webClient.delete()
                .uri("/{id}", id)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}

