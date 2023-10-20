package com.capgemini.gymapp.services.impl;

import com.capgemini.gymapp.entities.MemberShip;
import com.capgemini.gymapp.entities.Schedule;
import com.capgemini.gymapp.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {


    @Autowired
    private JavaMailSender javaMailSender;
    public void sendActivationEmail(User user, String activationToken) {
        String activationLink = "http://localhost:8089/api/v1/auth/activate?token=" + activationToken;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Activation Email");
        message.setText("Dear " + user.getFirstName() + ",\n\nYour Account must be activated.\n\nClick the following link to activate your account:\n\n" + activationLink + "\n\nThank you for choosing our services.");
        this.javaMailSender.send(message);
    }

    public void sendMemberShipConfirmationEmail(User user, MemberShip memberShip) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Member Confirmation");
        message.setText("Dear " + user.getFirstName() + ",\n\nYour MemberShip (ID: " + memberShip.getId() + ") has been created.\n\nThank you for choosing our services.");

        this.javaMailSender.send(message);
    }
    public void sendExpirationEmail(User user) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("MemberShip Expiration Notification");
        message.setText("Dear" + user.getFirstName() + ",\n\nYour MemberShip is going to expire soon.\n\nPlease take appropriate action.\n\nRegards,\nThank you for choosing our services.");

        this.javaMailSender.send(message);
    }

    public void sendScheduleConfirmationEmail(User user, Schedule schedule) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Schedule Confirmation");
        message.setText("Dear " + user.getFirstName() + ",\n\nYour Session of " + schedule.getName() + ") has been created.\n\nThank you.");
        this.javaMailSender.send(message);
    }


}
