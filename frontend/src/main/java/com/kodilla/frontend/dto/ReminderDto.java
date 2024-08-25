package com.kodilla.frontend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReminderDto {
    private Long id;
    private LocalDateTime reminderTime;
    private Long patientId;
    private Long appointmentId;
}
