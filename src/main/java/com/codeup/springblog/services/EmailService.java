package com.codeup.springblog.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service("mailService")
public class EmailService {

//    @Autowired
    public JavaMailSender emailSender;

    @Value("${spring.mail.from}")
    private String from;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void prepareAndSend(Notifier notifier, String subject, String body) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(notifier.getUser().getEmail());
        msg.setSubject(subject);
        msg.setText(body);

        RunnableMailSender runnableMailSender = new RunnableMailSender( msg, this);
        runnableMailSender.start();
    }
}
