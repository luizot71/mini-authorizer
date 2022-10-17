package br.com.api.vr.benefits.miniauthorizer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "transaction")
public class BenefitsCardTransactionEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id", unique = true, nullable = false )
    private Long cardId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private BenefitsCardEntity cardNumber;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @CreationTimestamp
    @Temporal( TemporalType.TIMESTAMP )
    private Date createdAt;

    @UpdateTimestamp
    @Temporal( TemporalType.TIMESTAMP )
    private Date updatedAt;
}
