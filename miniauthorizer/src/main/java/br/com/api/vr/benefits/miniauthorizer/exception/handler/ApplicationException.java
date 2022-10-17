package br.com.api.vr.benefits.miniauthorizer.exception.handler;

import br.com.api.vr.benefits.miniauthorizer.model.errors.BenefitsCardErrorModel;
import lombok.Getter;
import lombok.Setter;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Setter(PROTECTED)
public class ApplicationException extends Exception {

    private String code;
    private String detail;

    public ApplicationException() {
        super();
    }

    @Getter
    private BenefitsCardErrorModel benefitsCardErrorModel;

    public ApplicationException(final String title, final String code, final String detail) {
        super(title);
        this.code = code;
        this.detail = detail;
    }

    public ApplicationException(BenefitsCardErrorModel benefitsCardErrorModel, Throwable cause) {
        super(benefitsCardErrorModel.getMessage(), cause);
        this.benefitsCardErrorModel = benefitsCardErrorModel;
    }

    public ApplicationException(BenefitsCardErrorModel benefitsCardErrorModel) {
        this(benefitsCardErrorModel, null);
    }

    public String getTitulo() {
        return getMessage();
    }

    @Override
    public String toString() {
        return "ApplicationException{" +
                "code='" + code + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
