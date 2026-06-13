package com.empire.JeopardyService.service;

import com.empire.JeopardyService.model.GameRecord;
import com.empire.JeopardyService.model.Buzzer;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class BuzzerRegistrationService {

    public boolean registerBuzzer(Buzzer buzzer) {
        if (!GameRecord.fetchBuzzer(buzzer)) {
            buzzer.setLockout(LocalDateTime.MIN);
            buzzer.setScore(0);
            GameRecord.buzzers.add(buzzer);
            return true;
        }
        return false;
    }

    public void removeBuzzer(String buzzerId) throws BadRequestException {
        if (GameRecord.fetchBuzzer(buzzerId).isPresent()) {
            GameRecord.removeBuzzer(buzzerId);
            return;
        }
        throw new BadRequestException("Buzzer does not exist");
    }
}
