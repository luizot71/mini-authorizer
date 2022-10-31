package api.exception;

import api.model.errors.CardErrorModel;
import lombok.Getter;

public class CardModelException extends RuntimeException {

    @Getter
    private CardErrorModel model;

    public CardModelException(CardErrorModel model, Throwable cause) {
        super(model.getMessage(), cause);
        this.model = model;
    }

    public CardModelException(CardErrorModel model) {
        this(model, null);
    }

    public CardModelException() {

    }
}
