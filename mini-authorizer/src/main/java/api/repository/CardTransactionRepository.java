package api.repository;

import api.entity.CardTransactionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardTransactionRepository extends CrudRepository<CardTransactionEntity, Long > {

    List<CardTransactionEntity> findAll();

}
