package com.empire.JeopardyService.service;

import com.empire.JeopardyService.config.GameConfig;
import com.empire.JeopardyService.model.Buzzer;
import com.empire.JeopardyService.model.GameRecord;
import com.empire.JeopardyService.model.ScoreRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class GameLogicService {

    private static final Long NANOSECONDS_PER_MILLISECOND = 1000000L;

    public void buzzIn(String buzzerId) throws BadRequestException {
        try {
            if (GameConfig.setupMode) {
                setupBuzzer(buzzerId);
                return;
            }

            LocalDateTime buzzTime = LocalDateTime.now();

            Buzzer buzzer = fetchBuzzer(buzzerId);
            if (isValidBuzz(buzzer, buzzTime)) {
                buzzer.setBuzzed(buzzTime);
                log.info("Sending API message to UI to add user to stack...");
            }
            buzzer.setLockout(buzzTime.plusNanos(GameConfig.lockoutDuration * NANOSECONDS_PER_MILLISECOND));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    private void setupBuzzer(String buzzerId) {
        GameRecord.lastBuzzerId = buzzerId;
    }

    private boolean isValidBuzz(Buzzer buzzer, LocalDateTime buzzTime) {
        return buzzer.getBuzzed() == null && buzzTime.isAfter(buzzer.getLockout()) && GameRecord.buzzersEnabled;
    }

    private Buzzer fetchBuzzer(String buzzerId) throws BadRequestException {
        return GameRecord.buzzers.stream().filter(b -> b.getBuzzerId().equalsIgnoreCase(buzzerId)).findAny()
                .orElseThrow(() -> new BadRequestException("Buzzer " + buzzerId + " not found"));
    }

    public void resetBuzzers() {
        GameRecord.buzzers.forEach(buzzer -> buzzer.setBuzzed(null));
        GameRecord.buzzers.forEach(buzzer -> buzzer.setLockout(LocalDateTime.MIN));
        GameRecord.buzzersEnabled = false;
    }

    public void enableBuzzers() {
        GameRecord.buzzersEnabled = true;
    }

    public void scoreBuzzer(ScoreRequest scoreRequest) throws BadRequestException {
        Buzzer buzzer = GameRecord.fetchBuzzer(scoreRequest.getBuzzerId()).orElseThrow(() -> new BadRequestException("Buzzer not found"));
        buzzer.setScore(buzzer.getScore() + calculateScoreChange(scoreRequest));
        if (scoreRequest.isCorrect()) {
            resetBuzzers();
            return;
        }
        buzzer.setBuzzed(null);
    }

    public List<Buzzer> getAnswerOrder() {
        return GameRecord.buzzers.stream().filter(b -> Objects.nonNull(b.getBuzzed()))
                .sorted(Comparator.comparing(Buzzer::getBuzzed)).toList();
    }

    private int calculateScoreChange(ScoreRequest scoreRequest) {
        return scoreRequest.isCorrect() ? scoreRequest.getValue() : (int) (scoreRequest.getValue() * -1 * GameConfig.wrongAnswerModifier);
    }
}
