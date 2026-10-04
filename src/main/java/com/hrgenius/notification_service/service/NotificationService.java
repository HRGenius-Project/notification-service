package com.hrgenius.notification_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@hrgenius.com");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);

            // Development me console logging + actual attempt
            log.info("--------------------------------------------------");
            log.info("[NOTIFICATION-SERVICE] DISPATCHING EMAIL");
            log.info("To: {}", to);
            log.info("Subject: {}", subject);
            log.info("Content:\n{}", body);
            log.info("--------------------------------------------------");

            mailSender.send(message);
        } catch (Exception e) {
            log.warn("SMTP host not connected or mock mode active. Logged notification to console: {}", e.getMessage());
        }
    }
}