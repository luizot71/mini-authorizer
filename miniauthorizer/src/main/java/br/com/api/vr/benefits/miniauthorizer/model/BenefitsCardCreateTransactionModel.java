package br.com.api.vr.benefits.miniauthorizer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BenefitsCardCreateTransactionModel {

    private String cardNumber;

    private String password;

    private BigDecimal balance;
}
