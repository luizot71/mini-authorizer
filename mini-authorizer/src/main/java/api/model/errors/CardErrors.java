package api.model.errors;

import org.springframework.http.HttpStatus;

public class CardErrors {

    public static final CardErrorModel NOT_FOUND = new CardErrorModel(HttpStatus.NOT_FOUND.value(), "404001", "Nenhum cartão encontrado.");
    public static final CardErrorModel INVALID_NUMBER_CARD = new CardErrorModel(HttpStatus.NOT_FOUND.value(), "404001", "");
    public static final CardErrorModel ERROR_CREATING = new CardErrorModel(HttpStatus.BAD_REQUEST.value(), "400001", "Erro ao criar novo cartão. Tente novamente.");
    public static final CardErrorModel CARD_EXISTS = new CardErrorModel(HttpStatus.UNPROCESSABLE_ENTITY.value(), "422001", "Número do cartão já cadastrado. Tente novamente com outro número.");

}