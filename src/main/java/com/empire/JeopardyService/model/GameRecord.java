package com.empire.JeopardyService.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class GameRecord {
    public static List<Buzzer> buzzers;
    public static boolean buzzersEnabled;
    public static String lastBuzzerId;

    public static void init() {
        if (buzzers == null) {
            buzzers = new ArrayList<>();
        }
        buzzersEnabled = false;
    }

    public static boolean fetchBuzzer(Buzzer buzzer) {
        return buzzers.stream().anyMatch(b -> b.getBuzzerId().equalsIgnoreCase(buzzer.getBuzzerId())
                || b.getUserName().equalsIgnoreCase(buzzer.getUserName()));
    }

    public static Optional<Buzzer> fetchBuzzer(String buzzerId) {
        return buzzers.stream().filter(b -> b.getBuzzerId().equalsIgnoreCase(buzzerId)).findAny();
    }

    public static void removeBuzzer(String buzzerId) {
        buzzers.stream().filter(b -> b.getBuzzerId().equalsIgnoreCase(buzzerId)).findAny().ifPresent(t -> buzzers.remove(t));
    }

}
