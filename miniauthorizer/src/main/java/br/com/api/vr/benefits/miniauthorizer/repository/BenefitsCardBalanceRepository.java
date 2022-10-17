package br.com.api.vr.benefits.miniauthorizer.repository;

import br.com.api.vr.benefits.miniauthorizer.entity.BenefitsCardBalanceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BenefitsCardBalanceRepository extends CrudRepository<BenefitsCardBalanceEntity, Long > {
}
