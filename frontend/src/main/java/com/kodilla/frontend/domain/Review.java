package com.kodilla.frontend.domain;


import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    private Long id;
    private int rating;
    private String content;
    private Doctor doctor;
    private Patient patient;
}
