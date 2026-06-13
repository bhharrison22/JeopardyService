package com.empire.JeopardyService;

import com.empire.JeopardyService.config.GameConfig;
import com.empire.JeopardyService.model.GameRecord;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JeopardyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JeopardyServiceApplication.class, args);
	}

	@PostConstruct
	public void init() {
		GameRecord.init();
		GameConfig.lockoutDuration = 250L;
		GameConfig.setupMode = false;
		GameConfig.wrongAnswerModifier = 0.50;
	}


}
