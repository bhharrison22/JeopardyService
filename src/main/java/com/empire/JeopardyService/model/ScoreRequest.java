package com.empire.JeopardyService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@AllArgsConstructor
@ToString
public class ScoreRequest {
    private String buzzerId;
    private Integer value;
    private boolean correct;
}
