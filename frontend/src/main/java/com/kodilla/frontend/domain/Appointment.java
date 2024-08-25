package com.kodilla.frontend.domain;


import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {


    private Long id;
    private LocalDateTime dateTime;
    private Doctor doctor;
    private Patient patient;
}