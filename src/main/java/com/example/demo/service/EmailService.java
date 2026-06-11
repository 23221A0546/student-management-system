package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendNoticeMail(
            String email,
            String title,
            String description) {

        try {

            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(email);
            message.setSubject(title);
            message.setText(description);
            message.setFrom("akthecomrade@gmail.com");

            mailSender.send(message);

            System.out.println("Email sent to: " + email);

        } catch (Exception e) {

            System.out.println("Email failed for: " + email);
            e.printStackTrace();
        }
    }
}