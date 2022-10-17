package br.com.api.vr.benefits.miniauthorizer.repository;

import br.com.api.vr.benefits.miniauthorizer.entity.BenefitsCardEntity;
import br.com.api.vr.benefits.miniauthorizer.enums.BenefitsCardStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BenefitsCardRepository extends CrudRepository<BenefitsCardEntity, Long > {

    Optional<BenefitsCardEntity> findBenefitsCardByNumber(String cardNumber );

    BenefitsCardEntity save(BenefitsCardEntity benefitsCardEntity, String token);

    Optional<BenefitsCardEntity> findById(Long cardId, String token);

    List<BenefitsCardEntity> findAllBenefitsCardsAsc(BenefitsCardStatus benefitsCardStatus);

    void deleteById(Long id, String token);

    boolean existsById(Long id, String token);

    Optional<BenefitsCardEntity> findByCardNumber(String cardNumber, final String token);

    List<BenefitsCardEntity> findBenefitsCardByStatusCardOrderByAsc(BenefitsCardStatus status, final String token);
}
