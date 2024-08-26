package com.kodilla.frontend.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentDto {
    private Long id;
    private LocalDateTime dateTime;
    private DoctorDto doctor;
    private PatientDto patient;
    private Double price;
    private Double pricePln;
    private String currency;
}
