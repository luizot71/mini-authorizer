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

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "balance")
public class BenefitsCardBalanceEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id", unique = true, nullable = false )
    private Long cardId;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance = BigDecimal.valueOf(500);

    @CreationTimestamp
    @Temporal( TemporalType.TIMESTAMP )
    private Date createdAt;

    @UpdateTimestamp
    @Temporal( TemporalType.TIMESTAMP )
    private Date updatedAt;
}
