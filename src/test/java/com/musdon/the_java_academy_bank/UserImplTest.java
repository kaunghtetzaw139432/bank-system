package com.musdon.the_java_academy_bank;
import com.musdon.the_java_academy_bank.Repos.UserRepo;
import com.musdon.the_java_academy_bank.dtos.*;
import com.musdon.the_java_academy_bank.impls.UserImpl;
import com.musdon.the_java_academy_bank.models.User;
import com.musdon.the_java_academy_bank.services.EmailService;
import com.musdon.the_java_academy_bank.services.TranscationService;
import com.musdon.the_java_academy_bank.utils.AccountUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserImplTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private EmailService emailService;

    @Mock
    private TranscationService transcationService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserImpl userService;

    private User sourceUser;
    private User destinationUser;

    @BeforeEach
    void setUp() {
       
        sourceUser = User.builder()
                .firstName("Kaung")
                .lastName("Htet")
                .accountNumber("123456789")
                .accountBalance(new BigDecimal("5000.00"))
                .email("source@gmail.com")
                .build();

        destinationUser = User.builder()
                .firstName("Zayar")
                .lastName("Min")
                .accountNumber("987654321")
                .accountBalance(new BigDecimal("1000.00"))
                .email("dest@gmail.com")
                .build();
    }

    @Test
    void createAccount_Success_Test() {
        // Arrange
        UserRequest request = UserRequest.builder()
                .firstName("Kaung")
                .lastName("Htet")
                .email("kaung@gmail.com")
                .password("password")
                .build();

        when(userRepo.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("hashed_password");
        when(userRepo.save(any(User.class))).thenReturn(sourceUser);

        // Act
        BankResponse response = userService.createAccount(request);

        // Assert
        assertEquals(AccountUtils.ACCOUNT_CREATION_SUCCESS, response.getResponseCode());
        verify(emailService, times(1)).sendEmailAlert(any(EmailDetails.class));
        verify(userRepo, times(1)).save(any(User.class));
    }

    @Test
    void transfer_Successful_Test() {
        // Arrange
        TransferRequest request = TransferRequest.builder()
                .sourceAccountNumber("123456789")
                .destinationAccountNumber("987654321")
                .amount(new BigDecimal("1000.00"))
                .build();

        when(userRepo.existsByAccountNumber("987654321")).thenReturn(true);
        when(userRepo.findByAccountNumber("123456789")).thenReturn(sourceUser);
        when(userRepo.findByAccountNumber("987654321")).thenReturn(destinationUser);

        // Act
        BankResponse response = userService.transfer(request);

        // Assert
        assertEquals(AccountUtils.TRANSFER_SUCCESSFUL_CODE, response.getResponseCode());

        assertEquals(new BigDecimal("4000.00"), sourceUser.getAccountBalance());

        assertEquals(new BigDecimal("2000.00"), destinationUser.getAccountBalance());


        verify(userRepo, times(2)).save(any(User.class));
    }

    @Test
    void transfer_InsufficientBalance_Test() {

        TransferRequest request = TransferRequest.builder()
                .sourceAccountNumber("123456789")
                .destinationAccountNumber("987654321")
                .amount(new BigDecimal("6000.00"))
                .build();

        when(userRepo.existsByAccountNumber("987654321")).thenReturn(true);
        when(userRepo.findByAccountNumber("123456789")).thenReturn(sourceUser);

        BankResponse response = userService.transfer(request);


        assertEquals(AccountUtils.INSUFFICIENT_BALANCE_CODE, response.getResponseCode());

        verify(userRepo, never()).save(any(User.class));
    }
}
