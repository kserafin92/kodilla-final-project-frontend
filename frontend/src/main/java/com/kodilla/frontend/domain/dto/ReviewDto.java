package com.kodilla.frontend.domain.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {
    private Long id;
    private int rating;
    private String content;
    private DoctorDto doctor;
    private PatientDto patient;
    public String getDoctorLastName(){
        return doctor.getLastName();
    }
    public String getPatientLastName(){
        return patient.getLastName();
    }
}
