package com.musdon.the_java_academy_bank.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "transcation")
public class Transcation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String transcationId;

    private String transcationType;
    private BigDecimal amount;
    private String accountNumber;
    private String status;

    private LocalDate createdAt;
    private LocalDateTime modifiedAt; // ✅ new field

    // Save လုပ်ချိန် createdAt assign
    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDate.now();
        }
        this.modifiedAt = LocalDateTime.now();
    }

    // Update လုပ်ချိန် modifiedAt assign
    @PreUpdate
    protected void onUpdate() {
        this.modifiedAt = LocalDateTime.now();
    }
}
