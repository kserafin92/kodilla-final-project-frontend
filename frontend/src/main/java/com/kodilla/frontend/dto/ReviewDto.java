package com.kodilla.frontend.dto;
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
    private Long doctorId;
    private Long patientId;
}
