package com.empire.JeopardyService.controller;

import com.empire.JeopardyService.config.GameConfig;
import com.empire.JeopardyService.model.GameRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {

    @PostMapping(path = "/api/config/lockout/{duration}")
    public void setLockOutDuration(@PathVariable Long duration) {
        GameConfig.lockoutDuration = duration;
    }

    @PostMapping(path = "/api/config/setupMode/{enabled}")
    public void setLockOutDuration(@PathVariable Boolean enabled) {
        GameConfig.setupMode = enabled;
    }

    @PostMapping(path = "/api/config/modifer/{modifier}")
    public void setLockOutDuration(@PathVariable Double modifier) {
        GameConfig.wrongAnswerModifier = modifier;
    }

    @GetMapping(path = "/api/config/debug")
    public ResponseEntity<String> getConfigs() {
        String response = "Lockout Duration: " + GameConfig.lockoutDuration;
        response += "\nBuzzers Enabled: " + GameRecord.buzzersEnabled;
        response += "\nSetup Mode Enabled: " + GameConfig.setupMode;
        response += "\nLast Buzzer Used: " + GameRecord.lastBuzzerId;
        response += "\nWrong Answer Modifier: " + GameConfig.wrongAnswerModifier;
        return ResponseEntity.ok(response);
    }
}
