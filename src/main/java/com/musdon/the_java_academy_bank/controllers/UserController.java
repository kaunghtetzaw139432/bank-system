package com.musdon.the_java_academy_bank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.musdon.the_java_academy_bank.dtos.BankResponse;
import com.musdon.the_java_academy_bank.dtos.CreditDebitRequest;
import com.musdon.the_java_academy_bank.dtos.EnquiryRequest;
import com.musdon.the_java_academy_bank.dtos.LoginDto;
import com.musdon.the_java_academy_bank.dtos.TransferRequest;
import com.musdon.the_java_academy_bank.dtos.UserRequest;
import com.musdon.the_java_academy_bank.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User Account Managment APIs")
@AllArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;

    @Operation(summary = "Create New User Account", description = "Creating a new user and assigning an acccount ID")
    @ApiResponse(responseCode = "201", description = "Http Status 201 CREATED")
    @PostMapping
    public BankResponse createAccount(@RequestBody UserRequest userRequest) {
        return userService.createAccount(userRequest);
    }

    @Operation(summary = "Balance Enquiry", description = "Given an account number,check how much the user has")
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")

    @PostMapping("balanceEnquiry")
    public BankResponse balanceEnquiry(@RequestBody EnquiryRequest enquiryRequest) {
        return userService.balanceEnquiry(enquiryRequest);
    }

    @PostMapping("nameEnquiry")
    public String nameEnquiry(@RequestBody EnquiryRequest enquiryRequest) {
        return userService.nameEnquiry(enquiryRequest);
    }

    @PostMapping("credit")
    public BankResponse creditAccount(@RequestBody CreditDebitRequest request) {
        return userService.creditAccount(request);
    }

    @PostMapping("debit")
    public BankResponse debitAccount(@RequestBody CreditDebitRequest request) {
        return userService.debitAccount(request);
    }

    @PostMapping("transfer")
    public BankResponse trasfer(@RequestBody TransferRequest request) {
        return userService.transfer(request);
    }

    @PostMapping("/login")
    public BankResponse login(@RequestBody LoginDto loginDto) {
        return userService.login(loginDto);
    }

}
