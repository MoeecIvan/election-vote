package test.ivan.vote.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import test.ivan.vote.service.EmailService;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Async
    @Override
    public void send(Long electionId) {
        log.info("{} sending email...", electionId);
    }
}
