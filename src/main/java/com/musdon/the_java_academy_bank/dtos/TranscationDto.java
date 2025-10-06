package com.musdon.the_java_academy_bank.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TranscationDto {
    private String transcationType;
    private BigDecimal amount;
    private String accountNumber;
    private String status;
}
