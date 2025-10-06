package com.musdon.the_java_academy_bank.controllers;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;
import com.musdon.the_java_academy_bank.impls.BankStatement;
import com.musdon.the_java_academy_bank.models.Transcation;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/bankStatement")
@AllArgsConstructor
public class TranscationController {
    @Autowired

    private final BankStatement bankStatement;

    @GetMapping
    public List<Transcation> generateBankStatement(
            @RequestParam String accountNumber,
            @RequestParam String startDate,
            @RequestParam String endDate)throws DocumentException,FileNotFoundException {
        return bankStatement.generateStatement(accountNumber, startDate, endDate);
    }
}
