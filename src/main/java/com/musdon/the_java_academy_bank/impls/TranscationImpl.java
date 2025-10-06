package com.musdon.the_java_academy_bank.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.musdon.the_java_academy_bank.Repos.TranscationRepo;
import com.musdon.the_java_academy_bank.dtos.TranscationDto;
import com.musdon.the_java_academy_bank.models.Transcation;
import com.musdon.the_java_academy_bank.services.TranscationService;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class TranscationImpl implements TranscationService {
    @Autowired
    private final TranscationRepo transcationRepo;

    @Override
    public void saveTranscation(TranscationDto transcationDto) {
             Transcation transcation=Transcation.builder()
             .transcationType(transcationDto.getTranscationType())
             .accountNumber(transcationDto.getAccountNumber())
             .amount(transcationDto.getAmount())
             .status("SUCCESS")
             .build();
             transcationRepo.save(transcation);
             System.out.println("Transcation saved successfully");
    }

}
