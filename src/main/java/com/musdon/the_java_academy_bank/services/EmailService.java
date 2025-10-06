package com.musdon.the_java_academy_bank.services;

import org.springframework.stereotype.Service;

import com.musdon.the_java_academy_bank.dtos.EmailDetails;

@Service
public interface EmailService {
    void sendEmailAlert(EmailDetails emailDetails);

    void sendEmailWithAttachment(EmailDetails emailDetails);
}
