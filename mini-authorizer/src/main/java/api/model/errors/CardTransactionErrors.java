package api.model.errors;

import org.springframework.http.HttpStatus;

public class CardTransactionErrors {

    public static final CardErrorModel NOT_FOUND = new CardErrorModel(HttpStatus.NOT_FOUND.value(), "404001", "Nenhuma transação encontrada.");
    public static final CardErrorModel INSUFFICIENT_BALANCE = new CardErrorModel(HttpStatus.UNPROCESSABLE_ENTITY.value(), "422001", "Saldo insuficiente para realizar a transação.");
    public static final CardErrorModel INVALID_PASSWORD = new CardErrorModel(HttpStatus.UNPROCESSABLE_ENTITY.value(), "422001", "Senha inválida. Tente novamente.");
    public static final CardErrorModel INVALID_NUMBER_CARD = new CardErrorModel(HttpStatus.UNPROCESSABLE_ENTITY.value(), "422001", "Número do cartão não encontrado. Tente novamente.");
    public static final CardErrorModel INATIVE_CARD = new CardErrorModel(HttpStatus.UNPROCESSABLE_ENTITY.value(), "422001", "Cartão inativo.");

}