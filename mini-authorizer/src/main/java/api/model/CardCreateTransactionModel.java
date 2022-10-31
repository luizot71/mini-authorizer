package api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardCreateTransactionModel {

    private String numeroCartao;

    private String senha;

    private BigDecimal valor;

}
