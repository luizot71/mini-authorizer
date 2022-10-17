package br.com.api.vr.benefits.miniauthorizer.entity;

import br.com.api.vr.benefits.miniauthorizer.enums.BenefitsCardStatus;
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
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "benefitsCard")
public class BenefitsCardEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id", unique = true, nullable = false )
    private Long cardId;

    @Column(name = "cardNumber", nullable = false)
    @NotBlank( message = "Card number is required" )
    private String cardNumber;

    @Column(name = "password")
    private String password;

    @CreationTimestamp
    @Temporal( TemporalType.TIMESTAMP )
    private Date createdAt;

    @UpdateTimestamp
    @Temporal( TemporalType.TIMESTAMP )
    private Date updatedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "balance", nullable = false)
    private BenefitsCardBalanceEntity balance;

    private BenefitsCardStatus benefitsCardStatus;

}
