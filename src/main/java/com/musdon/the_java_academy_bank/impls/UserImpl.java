package com.musdon.the_java_academy_bank.impls;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.musdon.the_java_academy_bank.Repos.UserRepo;
import com.musdon.the_java_academy_bank.config.JwtTokenProvider;
import com.musdon.the_java_academy_bank.dtos.AccountInfo;
import com.musdon.the_java_academy_bank.dtos.BankResponse;
import com.musdon.the_java_academy_bank.dtos.CreditDebitRequest;
import com.musdon.the_java_academy_bank.dtos.EmailDetails;
import com.musdon.the_java_academy_bank.dtos.EnquiryRequest;
import com.musdon.the_java_academy_bank.dtos.LoginDto;
import com.musdon.the_java_academy_bank.dtos.TranscationDto;
import com.musdon.the_java_academy_bank.dtos.TransferRequest;
import com.musdon.the_java_academy_bank.dtos.UserRequest;
import com.musdon.the_java_academy_bank.models.Role;
import com.musdon.the_java_academy_bank.models.User;
import com.musdon.the_java_academy_bank.services.EmailService;
import com.musdon.the_java_academy_bank.services.TranscationService;
import com.musdon.the_java_academy_bank.services.UserService;
import com.musdon.the_java_academy_bank.utils.AccountUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserImpl implements UserService {

        @Autowired
        private final UserRepo userRepo;
        @Autowired
        private final EmailService emailService;
        @Autowired
        private final TranscationService transcationService;
        @Autowired
        private final PasswordEncoder passwordEncoder;
        @Autowired
        AuthenticationManager authenticationManager;
        @Autowired
        JwtTokenProvider jwtTokenProvider;

        @Override
        public BankResponse createAccount(UserRequest userRequest) {
                if (userRepo.existsByEmail(userRequest.getEmail())) {
                        return BankResponse.builder()
                                        .responseCode(AccountUtils.ACCOUNT_EXIST_CODE)
                                        .responseMessage(AccountUtils.ACCOUNT_EXIST_MESSAGE)
                                        .accountInfo(null)
                                        .build();

                }
                User newUser = User.builder()
                                .firstName(userRequest.getFirstName())
                                .lastName(userRequest.getLastName())
                                .otherName(userRequest.getOtherName())
                                .gender(userRequest.getGender())
                                .address(userRequest.getAddress())
                                .stateOfOrigin(userRequest.getStateOfOrigin())
                                .accountNumber(AccountUtils.generateAccountNumber())
                                .email(userRequest.getEmail())
                                .password(passwordEncoder.encode(userRequest.getPassword()))
                                .accountBalance(BigDecimal.ZERO)
                                .phoneNumber(userRequest.getPhoneNumber())
                                .status("Active")
                                .role(Role.valueOf("ROLE_ADMIN"))
                                .dateOfBirth(userRequest.getDateOfBirth())
                                .alternativePhoneNumber(userRequest.getAlternativePhoneNumber())
                                .build();
                User saveUser = userRepo.save(newUser);
                EmailDetails emailDetails = EmailDetails.builder()
                                .recipient(saveUser.getEmail())
                                .subject("Account Creation")
                                .messageBody("Congratulations! Your account has been successfully Created.\n Your Account Deatils : \n"
                                                +
                                                "Account Name: " + saveUser.getFirstName() + " "
                                                + saveUser.getLastName() + " " + saveUser.getOtherName()
                                                + "\nAccount Number" + saveUser.getAccountNumber())
                                .build();
                emailService.sendEmailAlert(emailDetails);

                return BankResponse.builder()
                                .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                                .accountInfo(AccountInfo.builder()
                                                .accountBalance(saveUser.getAccountBalance())
                                                .accountNumber(saveUser.getAccountNumber())
                                                .accountName(
                                                                saveUser.getFirstName() + " " + saveUser.getLastName()
                                                                                + " " + saveUser.getOtherName())
                                                .build())
                                .build();

        }

        public BankResponse login(LoginDto loginDto) {
                Authentication authentication = null;
                authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
                EmailDetails loginAlert = EmailDetails.builder()
                                .subject("You logged in !")
                                .recipient(loginDto.getEmail())
                                .messageBody("YOu logged into your account.If you did not initiate,please cotact your bank")
                                .build();
                emailService.sendEmailAlert(loginAlert);
                return BankResponse.builder()
                                .responseCode("Login Success")
                                .responseMessage(jwtTokenProvider.generateToken(authentication))
                                .build();
        }

        @Override
        public BankResponse balanceEnquiry(EnquiryRequest enquiryRequest) {
                boolean isAccountExist = userRepo.existsByAccountNumber(enquiryRequest.getAccountNumber());
                if (!isAccountExist) {
                        return BankResponse.builder()
                                        .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                                        .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                                        .accountInfo(null)
                                        .build();
                }
                User foundUser = userRepo.findByAccountNumber(enquiryRequest.getAccountNumber());
                return BankResponse.builder()
                                .responseCode(AccountUtils.ACCOUNT_FOUNT_CODE)
                                .responseMessage(AccountUtils.ACCOUNT_FOUND_SUCCESS)
                                .accountInfo(AccountInfo.builder()
                                                .accountBalance(foundUser.getAccountBalance())
                                                .accountNumber(foundUser.getAccountNumber())
                                                .accountName(foundUser.getFirstName() + " " + foundUser.getLastName()
                                                                + " " + foundUser.getOtherName())
                                                .build())
                                .build();
        }

        @Override
        public String nameEnquiry(EnquiryRequest enquiryRequest) {
                boolean isAccountExist = userRepo.existsByAccountNumber(enquiryRequest.getAccountNumber());
                if (!isAccountExist) {
                        return AccountUtils.ACCOUNT_NOT_FOUND_MESSAGE;
                }
                User foundUser = userRepo.findByAccountNumber(enquiryRequest.getAccountNumber());
                return foundUser.getFirstName() + " " + foundUser.getLastName() + " " + foundUser.getOtherName();
        }

        @Override
        public BankResponse creditAccount(CreditDebitRequest request) {
                boolean isAccountExist = userRepo.existsByAccountNumber(request.getAccountNumber());
                if (!isAccountExist) {
                        return BankResponse.builder()
                                        .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                                        .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                                        .accountInfo(null)
                                        .build();
                }
                User userToCredit = userRepo.findByAccountNumber(request.getAccountNumber());
                userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(request.getAmount()));
                userRepo.save(userToCredit);

                // Save transcatiion
                TranscationDto transcationDto = TranscationDto.builder()
                                .accountNumber(userToCredit.getAccountNumber())
                                .transcationType("Credit")
                                .amount(request.getAmount())
                                .build();
                transcationService.saveTranscation(transcationDto);

                return BankResponse.builder()
                                .responseCode(AccountUtils.ACCOUNT_CREATED_SUCCESS)
                                .responseMessage(AccountUtils.ACCOUNT_CREATED_SUCCESS_MESSAGE)
                                .accountInfo(AccountInfo.builder()
                                                .accountName(userToCredit.getFirstName() + " "
                                                                + userToCredit.getLastName() + " "
                                                                + userToCredit.getOtherName())
                                                .accountBalance(userToCredit.getAccountBalance())
                                                .accountNumber(userToCredit.getAccountNumber())
                                                .build())
                                .build();
        }

        @Override
        public BankResponse debitAccount(CreditDebitRequest request) {
                boolean isAccountExist = userRepo.existsByAccountNumber(request.getAccountNumber());
                if (!isAccountExist) {
                        return BankResponse.builder()
                                        .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                                        .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                                        .accountInfo(null)
                                        .build();
                }
                User userToDebit = userRepo.findByAccountNumber(request.getAccountNumber());
                BigInteger availableBalance = userToDebit.getAccountBalance().toBigInteger();
                BigInteger debitAmount = request.getAmount().toBigInteger();
                if (availableBalance.intValue() < debitAmount.intValue()) {
                        return BankResponse.builder()
                                        .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                                        .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                                        .accountInfo(null)
                                        .build();

                }

                else {
                        userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(request.getAmount()));
                        userRepo.save(userToDebit);
                        TranscationDto transcationDto = TranscationDto.builder()
                                        .accountNumber(userToDebit.getAccountNumber())
                                        .transcationType("Debit")
                                        .amount(request.getAmount())
                                        .build();
                        transcationService.saveTranscation(transcationDto);
                        return BankResponse.builder()
                                        .responseCode(AccountUtils.SUFFICIENT_DEBIT_CODE)
                                        .responseMessage(AccountUtils.SUFFICIENT_DEBIT_MESSAGE)
                                        .accountInfo(AccountInfo.builder()
                                                        .accountName(userToDebit.getFirstName() + " "
                                                                        + userToDebit.getLastName() + " "
                                                                        + userToDebit.getOtherName())
                                                        .accountBalance(userToDebit.getAccountBalance())
                                                        .accountNumber(userToDebit.getAccountNumber())
                                                        .build())
                                        .build();
                }

        }

        @Override
        public BankResponse transfer(TransferRequest request) {
                // 1. Destination Account Exist Check
                boolean isDestinationAccountExist = userRepo
                                .existsByAccountNumber(request.getDestinationAccountNumber());
                if (!isDestinationAccountExist) {
                        return BankResponse.builder()
                                        .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                                        .responseMessage("Destination account not found")
                                        .accountInfo(null)
                                        .build();
                }

                // 2. Source Account Exist Check
                User sourceAccountUser = userRepo.findByAccountNumber(request.getSourceAccountNumber());
                if (sourceAccountUser == null) {
                        return BankResponse.builder()
                                        .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                                        .responseMessage("Source account not found")
                                        .accountInfo(null)
                                        .build();
                }

                // 3. Balance Check (if amount > balance => insufficient)
                if (request.getAmount().compareTo(sourceAccountUser.getAccountBalance()) > 0) {
                        return BankResponse.builder()
                                        .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                                        .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                                        .accountInfo(null)
                                        .build();
                }

                // 4. Debit Source Account
                sourceAccountUser.setAccountBalance(
                                sourceAccountUser.getAccountBalance().subtract(request.getAmount()));
                String sourceUserName = sourceAccountUser.getFirstName() + " "
                                + sourceAccountUser.getLastName() + " "
                                + sourceAccountUser.getOtherName();
                userRepo.save(sourceAccountUser);

                // 5. Send Debit Alert
                EmailDetails debitAlert = EmailDetails.builder()
                                .subject("Debit Alert")
                                .recipient(sourceAccountUser.getEmail())
                                .messageBody("The sum of " + request.getAmount()
                                                + " has been debited from your account. "
                                                + "Your current balance is " + sourceAccountUser.getAccountBalance())
                                .build();
                emailService.sendEmailAlert(debitAlert);
                TranscationDto transcationDto = TranscationDto.builder()
                                .accountNumber(sourceAccountUser.getAccountNumber())
                                .transcationType("Debit")
                                .amount(request.getAmount())
                                .build();
                transcationService.saveTranscation(transcationDto);

                // 6. Credit Destination Account
                User destinationAccountUser = userRepo.findByAccountNumber(request.getDestinationAccountNumber());
                destinationAccountUser.setAccountBalance(
                                destinationAccountUser.getAccountBalance().add(request.getAmount()));
                userRepo.save(destinationAccountUser);

                // 7. Send Credit Alert
                EmailDetails creditAlert = EmailDetails.builder()
                                .subject("Credit Alert")
                                .recipient(destinationAccountUser.getEmail())
                                .messageBody("The sum of " + request.getAmount()
                                                + " has been credited to your account from " + sourceUserName
                                                + ". Your current balance is "
                                                + destinationAccountUser.getAccountBalance())
                                .build();
                emailService.sendEmailAlert(creditAlert);
                TranscationDto transcation = TranscationDto.builder()
                                .accountNumber(destinationAccountUser.getAccountNumber())
                                .transcationType("Credit")
                                .amount(request.getAmount())
                                .build();
                transcationService.saveTranscation(transcation);

                // 8. Response
                return BankResponse.builder()
                                .responseCode(AccountUtils.TRANSFER_SUCCESSFUL_CODE)
                                .responseMessage(AccountUtils.TRANSFER_SUCCESSFUL_MESSAGE)
                                .accountInfo(null)
                                .build();
        }

}
