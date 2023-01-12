package com.aueb.springloginsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoggingService {
    @Autowired
    private LoggingRepository loggingRepository;

    public void logSuccessfulLogin(String username) {
        Logging logging = new Logging();
        logging.setUsername(username);
        logging.setLoginTime(LocalDateTime.now());
        loggingRepository.save(logging);
    }
}
