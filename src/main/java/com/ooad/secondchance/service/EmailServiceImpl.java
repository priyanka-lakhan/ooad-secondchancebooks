package com.ooad.secondchance.service;

import com.ooad.secondchance.config.MailConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by Priyanka on 4/3/21.
 */
@Service
public class EmailServiceImpl {
    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private MailConfig mailSenderConfig;

    public void sendEmail(String to, String subject, String text) {
        try {
            MimeMessage message = emailSender.createMimeMessage();

            message.setSubject(subject);
            MimeMessageHelper helper;
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(mailSenderConfig.getUsername());
            helper.setTo(to);
            helper.setText(text, true);
            emailSender.send(message);
        } catch (MessagingException ex) {
            throw new SCBookException("Could not send email: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
