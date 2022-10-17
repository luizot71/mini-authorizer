package br.com.api.vr.benefits.miniauthorizer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BenefitsCardCreateModel {

    private String cardNumber;

    private String password;
}
