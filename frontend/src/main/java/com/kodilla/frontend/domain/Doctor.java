package com.kodilla.frontend.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor {


    private Long id;


    private String firstName;


    private String lastName;


    private String specialization;


    private List<Review> reviews = new ArrayList<>();


    private List<Appointment> appointments = new ArrayList<>();
}

