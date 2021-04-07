package com.ooad.secondchance.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Base64;
import java.util.Properties;

/**
 * Created by Priyanka on 4/3/21.
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "mail.sender")
public class MailConfig {
    private String host;
    private String username;
    private String password;    //UExvb2FkQDIwMjE=
    private int port;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);

        mailSender.setUsername(username);
        mailSender.setPassword(new String(Base64.getDecoder().decode(password)));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
