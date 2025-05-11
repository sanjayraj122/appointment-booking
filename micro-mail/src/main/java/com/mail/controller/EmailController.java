package com.mail.controller;

import com.mail.entity.Email;
import com.mail.request.EmailDto;
import com.mail.service.EmailService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<Email> sendEmail(@RequestBody EmailDto emailDto) {
        System.out.println("in mail" +emailDto.toString());
        Email email = emailService.createEmail(emailDto);
        emailService.sendEmail(email);

        return new ResponseEntity<>(email, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<Iterable<Email>> list(@PageableDefault(size = 5) Pageable pageable) {
        return new ResponseEntity<>(emailService.list(pageable), HttpStatus.OK);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<Email> list(@PathVariable Long id) {
        return new ResponseEntity<>(emailService.list(id), HttpStatus.OK);
    }
}
