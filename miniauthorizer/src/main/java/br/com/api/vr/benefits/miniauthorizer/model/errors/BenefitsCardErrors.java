package br.com.api.vr.benefits.miniauthorizer.model.errors;

import org.springframework.http.HttpStatus;

public class BenefitsCardErrors {

    public static final BenefitsCardErrorModel BENEFITS_CARD_ERROR_CREATING = new BenefitsCardErrorModel(HttpStatus.BAD_REQUEST.value(), "500", "Error creating new card. Try again.");

    public static final BenefitsCardErrorModel BENEFITS_CARD_EXISTS = new BenefitsCardErrorModel(HttpStatus.UNPROCESSABLE_ENTITY.value(), "422", "Card number already registered. Try again with another number.");

    public static final BenefitsCardErrorModel BENEFITS_CARD_NOT_FOUND = new BenefitsCardErrorModel(HttpStatus.NOT_FOUND.value(), "404", "No cards found.");

}
