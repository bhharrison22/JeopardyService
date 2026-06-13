package com.empire.JeopardyService.controller;

import com.empire.JeopardyService.model.Buzzer;
import com.empire.JeopardyService.model.ScoreRequest;
import com.empire.JeopardyService.service.GameLogicService;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.*;

@RestController
public class GameLogicController {

    private final GameLogicService gameLogicService;

    public GameLogicController(GameLogicService gameLogicService) {
        this.gameLogicService = gameLogicService;
    }

    @GetMapping(path = "/api/buzzer/buzz/{buzzerId}")
    public void buzzIn(@PathVariable String buzzerId) throws BadRequestException {
        gameLogicService.buzzIn(buzzerId);
    }

    @PostMapping(path = "/api/buzzer/reset")
    public void resetBuzzers() {
        gameLogicService.resetBuzzers();
    }

    @PostMapping(path = "/api/buzzers/enable")
    public void enableBuzzers() {
        gameLogicService.enableBuzzers();
    }

    @PostMapping(path = "/api/buzzers/score")
    public void scoreQuestion(@RequestBody ScoreRequest scoreRequest) throws BadRequestException {
        gameLogicService.scoreBuzzer(scoreRequest);
    }


}
