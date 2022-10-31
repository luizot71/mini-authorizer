package api.repository;

import api.entity.CardEntity;
import api.enums.CardStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends CrudRepository<CardEntity, Long > {

    Optional<CardEntity> findByNumeroCartao(String numeroCartao );

    List<CardEntity> findAllByOrderByNumeroCartaoAsc();

    List<CardEntity> findAllByStatusOrderByNumeroCartaoAsc(CardStatus status );

}
