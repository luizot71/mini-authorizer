package api.repository;

import api.entity.CardBalanceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardBalanceRepository extends CrudRepository<CardBalanceEntity, Long > {

}
