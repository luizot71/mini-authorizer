package api.model.errors;

import org.springframework.http.HttpStatus;

public class CardCommonErrors {

    public static final CardErrorModel INVALID_PARAMETERS = new CardErrorModel(HttpStatus.BAD_REQUEST.value(), "400000", "Parâmetro inválido");

    public static final CardErrorModel UNAUTHORIZED = new CardErrorModel(HttpStatus.UNAUTHORIZED.value(), "401000", "Não autorizado");

    public static final CardErrorModel UNEXPECTED_ERROR = new CardErrorModel(HttpStatus.INTERNAL_SERVER_ERROR.value(), "500000", "Erro inesperado");

}
