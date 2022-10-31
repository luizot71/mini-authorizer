package api.entity;

import api.enums.CardStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "cartao")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id", unique = true, nullable = false )
    private Long id;

    @Column(name = "numeroCartao", nullable = false)
    @NotBlank( message = "Número do cartão é obrigatório" )
    private String numeroCartao;

    @Column(name = "senha")
    private String senha;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_saldo", nullable = false)
    private CardBalanceEntity saldo;

    private CardStatus status;

    @CreationTimestamp
    @Temporal( TemporalType.TIMESTAMP )
    private Date createdAt;

    @UpdateTimestamp
    @Temporal( TemporalType.TIMESTAMP )
    private Date updatedAt;

}