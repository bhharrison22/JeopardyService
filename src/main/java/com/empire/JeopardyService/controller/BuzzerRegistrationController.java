package com.empire.JeopardyService.controller;

import com.empire.JeopardyService.model.Buzzer;
import com.empire.JeopardyService.model.GameRecord;
import com.empire.JeopardyService.service.BuzzerRegistrationService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class BuzzerRegistrationController {

    private final BuzzerRegistrationService buzzerRegistrationService;

    public BuzzerRegistrationController(BuzzerRegistrationService buzzerRegistrationService) {
        this.buzzerRegistrationService = buzzerRegistrationService;
    }

    @GetMapping(path = "/api/test")
    public ResponseEntity<String> testEndpoint() {
        return new ResponseEntity<>("Distributed Monoliths are a myth", HttpStatus.OK);
    }

    @GetMapping(path = "/api/buzzer/list")
    public ResponseEntity<List<Buzzer>> listBuzzers() {
        return new ResponseEntity<>(GameRecord.buzzers, HttpStatus.OK);
    }

    @PostMapping(path = "/api/buzzer/register")
    public ResponseEntity<String> registerBuzzer(@RequestBody Buzzer buzzer) {
        boolean success = buzzerRegistrationService.registerBuzzer(buzzer);
        return success ? new ResponseEntity<>("Registered!", HttpStatus.OK) : new ResponseEntity<>("It didn't work :(", HttpStatus.CONFLICT);
    }

    @DeleteMapping(path = "/api/buzzer/clear")
    public ResponseEntity<String> clearBuzzers() {
        GameRecord.init();
        return new ResponseEntity<>("Removed", HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/api/buzzer/remove/{buzzerId}")
    public ResponseEntity<String> removeBuzzer(@PathVariable String buzzerId) throws BadRequestException {
        buzzerRegistrationService.removeBuzzer(buzzerId);
        return new ResponseEntity<>("Removed", HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/api/buzzer/last")
    public ResponseEntity<String> lastBuzzer() {
        return new ResponseEntity<>(GameRecord.lastBuzzerId, HttpStatus.OK);
    }
}
