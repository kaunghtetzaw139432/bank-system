package com.musdon.the_java_academy_bank;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musdon.the_java_academy_bank.dtos.BankResponse;
import com.musdon.the_java_academy_bank.dtos.UserRequest;
import com.musdon.the_java_academy_bank.services.UserService;
import com.musdon.the_java_academy_bank.config.JwtTokenProvider;
import com.musdon.the_java_academy_bank.controllers.UserController;
import com.musdon.the_java_academy_bank.dtos.AccountInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false) 
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService; 
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private ObjectMapper objectMapper; 

    @Test
    @WithMockUser
    void createAccount_ShouldReturnCreated() throws Exception {
        // Arrange
        UserRequest userRequest = UserRequest.builder()
                .firstName("Kaung")
                .lastName("Htet")
                .email("kaung@gmail.com")
                .build();

        BankResponse mockResponse = BankResponse.builder()
                .responseCode("201")
                .responseMessage("Account Created Successfully")
                .accountInfo(AccountInfo.builder()
                        .accountNumber("12345")
                        .build())
                .build();

        when(userService.createAccount(any(UserRequest.class))).thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(post("/api/user")
                .with(csrf()) 
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk()) 
                .andExpect(jsonPath("$.responseCode").value("201"))
                .andExpect(jsonPath("$.accountInfo.accountNumber").value("12345"));
    }
}
