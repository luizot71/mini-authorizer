package br.com.api.vr.benefits.miniauthorizer.repository;

import br.com.api.vr.benefits.miniauthorizer.entity.BenefitsCardTransactionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface  BenefitsCardTransactionRepository extends CrudRepository<BenefitsCardTransactionEntity, Long > {

    List<BenefitsCardTransactionEntity> findAll();

    Optional<BenefitsCardTransactionEntity> findById(Long id, String token);
}
