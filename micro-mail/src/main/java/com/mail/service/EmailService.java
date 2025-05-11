package com.mail.service;

import com.mail.entity.Email;
import com.mail.entity.StatusEmail;
import com.mail.repository.EmailRepository;
import com.mail.request.EmailDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private JavaMailSender javaMailSender;


    public Email createEmail(EmailDto emailDto) {
        Email email = new Email();

        email.setSendDateEmail(LocalDateTime.now());
        BeanUtils.copyProperties(emailDto, email);

        return email;
    }

    public void sendEmail(Email email) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom(email.getEmailFrom());
            message.setTo(email.getEmailTo());
            message.setSubject(email.getSubject());
            message.setText(email.getText());

            javaMailSender.send(message);

            email.setStatusEmail(StatusEmail.SENT);
        } catch (Exception e) {
            email.setStatusEmail(StatusEmail.ERROR);
        } finally {
            emailRepository.save(email);
        }
    }


    public Page<Email> list(Pageable pageable) {

        return emailRepository.findAll(pageable);
    }

    public Email list(Long id) {

        return emailRepository.findById(id).orElseThrow();
    }
}
