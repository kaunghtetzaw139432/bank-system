package com.musdon.the_java_academy_bank.utils;

import java.time.Year;

public class AccountUtils {
    public static final String ACCOUNT_EXIST_CODE = "001";

    public static final String ACCOUNT_EXIST_MESSAGE = "This user already has an account created!";

    public static final String ACCOUNT_CREATION_SUCCESS = "002";

    public static final String ACCOUNT_CREATION_MESSAGE = "Account has been successfully created";

    public static final String ACCOUNT_NOT_EXIST_CODE = "003";

    public static final String ACCOUNT_NOT_EXIST_MESSAGE = "User with the provided Account Number does not exist";

    public static final String ACCOUNT_FOUNT_CODE = "004";

    public static final String ACCOUNT_FOUND_SUCCESS = "User Account Found";

    public static final String ACCOUNT_NOT_FOUND_MESSAGE = "User Account NOt Found";

    public static final String ACCOUNT_CREATED_SUCCESS = "005";

    public static final String ACCOUNT_CREATED_SUCCESS_MESSAGE = "User Account Found";

    public static final String INSUFFICIENT_BALANCE_CODE = "006";

    public static final String INSUFFICIENT_BALANCE_MESSAGE = "Insufficient balance";

    public static final String SUFFICIENT_DEBIT_CODE = "007";

    public static final String SUFFICIENT_DEBIT_MESSAGE = "Amount has been successfully dibited";

    public static final String TRANSFER_SUCCESSFUL_CODE = "008";

     public static final String TRANSFER_SUCCESSFUL_MESSAGE = "Transfer Successful";


    public static String generateAccountNumber() {
        Year currentYear = Year.now();
        int min = 100000;
        int max = 999999;
        int randNumber = (int) Math.floor(Math.random() * (max - min + 1) + min);

        // convert to string
        String year = String.valueOf(currentYear);
        String randomNumber = String.valueOf(randNumber);
        StringBuilder accountNumber = new StringBuilder();
        return accountNumber.append(year).append(randomNumber).toString();
    }

}
