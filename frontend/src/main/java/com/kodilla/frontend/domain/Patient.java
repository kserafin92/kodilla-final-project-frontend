package com.kodilla.frontend.domain;


import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {


    private Long id;


    private String firstName;


    private String lastName;


    private String email;


    private String phone;


    private List<Appointment> appointments;


    private List<Review> reviews;

}