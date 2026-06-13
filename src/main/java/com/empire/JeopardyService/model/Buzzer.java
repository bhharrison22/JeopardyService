package com.empire.JeopardyService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@ToString
public class Buzzer {
    private String buzzerId;
    private String userName;
    private LocalDateTime buzzed;
    private LocalDateTime lockout;
    private Integer score;
}
