package com.empire.JeopardyService.config;

import org.springframework.stereotype.Component;

@Component
public class GameConfig {
    public static Long lockoutDuration;
    public static boolean setupMode;
    public static double wrongAnswerModifier;
}
