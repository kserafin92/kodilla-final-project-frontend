package com.kodilla.frontend.domain;


import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reminder {
    private Long id;
    private LocalDateTime reminderTime;
    private Appointment appointment;
}
