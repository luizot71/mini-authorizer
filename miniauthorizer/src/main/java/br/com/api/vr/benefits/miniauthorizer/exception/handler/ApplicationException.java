package br.com.api.vr.benefits.miniauthorizer.exception.handler;

import br.com.api.vr.benefits.miniauthorizer.model.errors.BenefitsCardErrorModel;
import lombok.Getter;
import lombok.Setter;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Setter(PROTECTED)
public class ApplicationException extends Exception {

    private String codigo;
    private String detalhe;

    public ApplicationException() {
        super();
    }

    @Getter
    private BenefitsCardErrorModel model;

    public ApplicationException(final String titulo, final String codigo, final String detalhe) {
        super(titulo);
        this.codigo = codigo;
        this.detalhe = detalhe;
    }

    public ApplicationException(BenefitsCardErrorModel model, Throwable cause) {
        super(model.getMessage(), cause);
        this.model = model;
    }

    public ApplicationException(BenefitsCardErrorModel model) {
        this(model, null);
    }

    public String getTitulo() {
        return getMessage();
    }

    @Override
    public String toString() {
        return "ApplicationException{" +
                "codigo='" + codigo + '\'' +
                ", detalhe='" + detalhe + '\'' +
                '}';
    }
}
