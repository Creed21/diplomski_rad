package fon.classroom.booking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl  {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
        if(to == null || subject == null || text == null)
            throw new IllegalArgumentException("Invalid input parameters!");

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("nevalidanemail@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        emailSender.send(message);
    }
}
