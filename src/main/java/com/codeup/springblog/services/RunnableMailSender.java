package com.codeup.springblog.services;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;

public class RunnableMailSender implements Runnable {
    private Thread thread;
    private SimpleMailMessage message;
    EmailService emailService;

    RunnableMailSender(SimpleMailMessage message, EmailService emailService) {
        this.message = message;
        this.emailService = emailService;
    }

    public void run() {
        try {
            this.emailService.emailSender.send(message);
        } catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }
    }

    public void start () {
        if (thread == null) {
            thread = new Thread (this, "SendEmail");
            thread.start ();
        }
    }
}
