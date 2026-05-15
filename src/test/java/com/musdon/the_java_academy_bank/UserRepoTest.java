package com.musdon.the_java_academy_bank;

import com.musdon.the_java_academy_bank.Repos.UserRepo;
import com.musdon.the_java_academy_bank.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepoTest {
    @Autowired
    private UserRepo userRepo;

    @Test
    void should_ReturnTrue_When_EmailExists() {
        // Arrange
        User user = User.builder()
                .email("test@gmail.com")
                .accountNumber("12345")
                .build();
        userRepo.save(user);

        // Act
        Boolean exists = userRepo.existsByEmail("test@gmail.com");

        // Assert
        assertTrue(exists);
    }
}
