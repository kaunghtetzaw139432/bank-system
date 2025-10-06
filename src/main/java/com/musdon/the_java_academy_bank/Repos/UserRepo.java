package com.musdon.the_java_academy_bank.Repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.musdon.the_java_academy_bank.models.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
  Boolean existsByEmail(String email);

  Optional<User> findByEmail(String email);

  Boolean existsByAccountNumber(String accountNumber);

  User findByAccountNumber(String accountNumber);

}
