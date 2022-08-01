package com.rajblowplast.digital.sms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    @Autowired
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    private static String otpBody="Hello %s,\n\nYour OTP for registration is %s. It is valid for 10 minutes. Do not share it with anyone." +
            "\n\nRegards,\nTeam RBP.\n\n\nThis is system generated mail. Please do not reply to this.";

    // To send a simple email
    public void sendRegistrationOtpMail(String userName, String email, String otp) {
        // Try block to check for exceptions
        try {

            // Creating a simple mail message
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(email);
            mailMessage.setText(String.format(otpBody, userName, otp));
            mailMessage.setSubject("Registration OTP");

            // Sending the mail
            javaMailSender.send(mailMessage);
            logger.debug("Mail sent successfully.");
        } catch (Exception e) {
            logger.error("Error while sending mail---{}", e.getMessage());
        }
    }
}
