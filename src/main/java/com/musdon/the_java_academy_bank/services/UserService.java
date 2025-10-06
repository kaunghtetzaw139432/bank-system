package com.musdon.the_java_academy_bank.services;

import org.springframework.stereotype.Service;

import com.musdon.the_java_academy_bank.dtos.BankResponse;
import com.musdon.the_java_academy_bank.dtos.CreditDebitRequest;
import com.musdon.the_java_academy_bank.dtos.EnquiryRequest;
import com.musdon.the_java_academy_bank.dtos.LoginDto;
import com.musdon.the_java_academy_bank.dtos.TransferRequest;
import com.musdon.the_java_academy_bank.dtos.UserRequest;

@Service
public interface UserService  {
    BankResponse createAccount(UserRequest userRequest);

    BankResponse balanceEnquiry(EnquiryRequest enquiryRequest);

    String nameEnquiry(EnquiryRequest enquiryRequest);

    BankResponse creditAccount(CreditDebitRequest request);

    BankResponse debitAccount(CreditDebitRequest request);

    BankResponse transfer(TransferRequest request);

    BankResponse login(LoginDto loginDto);

}
