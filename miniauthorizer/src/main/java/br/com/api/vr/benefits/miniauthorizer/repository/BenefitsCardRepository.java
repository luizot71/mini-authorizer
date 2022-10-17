package br.com.api.vr.benefits.miniauthorizer.repository;

import br.com.api.vr.benefits.miniauthorizer.entity.BenefitsCardEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BenefitsCardRepository extends CrudRepository<BenefitsCardEntity, Long > {

    Optional<BenefitsCardEntity> findBenefitsCardByNumber(String cardNumber );

}
