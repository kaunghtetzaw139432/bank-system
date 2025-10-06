package com.musdon.the_java_academy_bank.services;

import org.springframework.stereotype.Service;

import com.musdon.the_java_academy_bank.dtos.TranscationDto;

@Service
public interface TranscationService {
     void saveTranscation(TranscationDto transcationDto);
}
