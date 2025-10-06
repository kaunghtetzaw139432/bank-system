package com.musdon.the_java_academy_bank.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.musdon.the_java_academy_bank.models.Transcation;

@Repository
public interface TranscationRepo extends JpaRepository<Transcation, String> {

}
