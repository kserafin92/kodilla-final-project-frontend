package com.kodilla.frontend.service;

import com.kodilla.frontend.domain.Appointment;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

@Service
public class AppointmentService {

    private final WebClient webClient;

    public AppointmentService() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/appointments")
                .build();
    }

    public List<Appointment> getAllAppointments() {
        return webClient.get()
                .retrieve()
                .bodyToFlux(Appointment.class)
                .collectList()
                .block();
    }

    public Appointment getAppointmentById(Long id) {
        return webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(Appointment.class)
                .block();
    }

    public Appointment createAppointment(Appointment appointment) {
        return webClient.post()
                .bodyValue(appointment)
                .retrieve()
                .bodyToMono(Appointment.class)
                .block();
    }

    public Appointment updateAppointment(Long id, Appointment appointment) {
        return webClient.put()
                .uri("/{id}", id)
                .bodyValue(appointment)
                .retrieve()
                .bodyToMono(Appointment.class)
                .block();
    }

    public void deleteAppointment(Long id) {
        webClient.delete()
                .uri("/{id}", id)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}

