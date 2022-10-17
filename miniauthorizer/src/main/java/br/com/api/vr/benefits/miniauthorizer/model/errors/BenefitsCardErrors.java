package br.com.api.vr.benefits.miniauthorizer.model.errors;

import org.springframework.http.HttpStatus;

public class BenefitsCardErrors {

    public static final BenefitsCardErrorModel BENEFITS_CARD_ERROR_CREATING = new BenefitsCardErrorModel(HttpStatus.BAD_REQUEST.value(), "500", "Error creating new benefits card. Try again.");

    public static final BenefitsCardErrorModel BENEFITS_CARD_EXISTS = new BenefitsCardErrorModel(HttpStatus.UNPROCESSABLE_ENTITY.value(), "422", "Benefits Card number already registered. Try again with another number.");

    public static final BenefitsCardErrorModel BENEFITS_CARD_NOT_FOUND = new BenefitsCardErrorModel(HttpStatus.NOT_FOUND.value(), "404", "No Benefits cards found.");

    public static final BenefitsCardErrorModel BENEFITS_CARD_INVALID_NUMBER_CARD = new BenefitsCardErrorModel(HttpStatus.NOT_FOUND.value(), "404", "");

    public static final BenefitsCardErrorModel BENEFITS_CARD_INSUFFICIENT_BALANCE = new BenefitsCardErrorModel(HttpStatus.UNPROCESSABLE_ENTITY.value(), "422", "Insufficient balance to carry out the transaction.");

    public static final BenefitsCardErrorModel BENEFITS_CARD_INVALID_PASSWORD = new BenefitsCardErrorModel(HttpStatus.UNPROCESSABLE_ENTITY.value(), "422", "Invalid password. Try again.");

    public static final BenefitsCardErrorModel BENEFITS_CARD_INATIVE_CARD = new BenefitsCardErrorModel(HttpStatus.UNPROCESSABLE_ENTITY.value(), "422", "Inactive Benefits Card.");
}
