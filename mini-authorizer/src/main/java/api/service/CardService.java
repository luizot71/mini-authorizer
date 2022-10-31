package api.service;

import api.entity.CardEntity;
import api.entity.CardBalanceEntity;
import api.enums.CardStatus;
import api.exception.CardModelException;
import api.model.CardModel;
import api.model.CardCreateModel;
import api.model.errors.CardErrors;
import api.repository.CardRepository;
import api.repository.CardBalanceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardBalanceRepository cardBalanceRepository;

    private ModelMapper mapper = new ModelMapper();

    public BigDecimal findCartaoByNumeroCartao(String numeroCartao ) {
        CardEntity cartao = cardRepository.findByNumeroCartao(numeroCartao).orElse( null );
        while ( cartao == null ) {
            throw new CardModelException(CardErrors.INVALID_NUMBER_CARD);
        }
        return cartao.getSaldo().getValor();
    }

    public CardModel findCartaoById(Long id ) {
        CardEntity cartao = cardRepository.findById( id ).orElse( new CardEntity() );
        while ( cartao.getId() == null ) {
            throw new CardModelException(CardErrors.NOT_FOUND);
        }
        return mapper.map(cartao, CardModel.class);
    }

    public List<CardModel> findAllByOrderByNumeroCartaoAsc() {
        List<CardEntity> cartoes = cardRepository.findAllByOrderByNumeroCartaoAsc();
        while ( cartoes.isEmpty() ) {
            throw new CardModelException(CardErrors.NOT_FOUND);
        }
        return cartoes.stream().map(entity -> mapper.map(entity, CardModel.class)).collect(Collectors.toList());
    }

    public List<CardModel> findAllByStatusOrderByNumeroCartaoAsc(CardStatus status ) {
        List<CardEntity> cartoes = cardRepository.findAllByStatusOrderByNumeroCartaoAsc( status );
        while ( cartoes.isEmpty() ) {
            throw new CardModelException(CardErrors.NOT_FOUND);
        }
        return cartoes.stream().map(entity -> mapper.map(entity, CardModel.class)).collect(Collectors.toList());
    }

    public CardModel save(CardCreateModel cardCreateModel) {
        CardEntity cartaoEntity = mapper.map(cardCreateModel, CardEntity.class);
        while ( isCardExist(cartaoEntity) ) {
            throw new CardModelException(CardErrors.CARD_EXISTS);
        }
        try {
            CardBalanceEntity cardBalanceEntity = new CardBalanceEntity();
            cardBalanceRepository.save(cardBalanceEntity);
            cartaoEntity.setSaldo(cardBalanceEntity);
            cartaoEntity.setStatus(CardStatus.ATIVO);
            cartaoEntity = cardRepository.save(cartaoEntity);
            return mapper.map(cartaoEntity, CardModel.class);
        } catch (Exception e) {
            throw new CardModelException(CardErrors.ERROR_CREATING);
        }
    }

    public CardModel update(Long id, CardEntity cartaoEntity) {
        CardEntity cartao = cardRepository.findById( id ).orElse( new CardEntity() );
        while ( cardRepository.existsById( id ) ) {
            cartaoEntity.setId(id);
            CardBalanceEntity cardBalanceEntity = (cartao.getSaldo() != null) ? cardBalanceRepository.findById( cartao.getSaldo().getId() ).orElse( new CardBalanceEntity() ) : new CardBalanceEntity();
            cartaoEntity.setSaldo(cardBalanceEntity);
            cartaoEntity.setNumeroCartao( cartaoEntity.getNumeroCartao() );
            cartaoEntity.setSenha( cartaoEntity.getSenha() );
            cartaoEntity.setStatus( (cartaoEntity.getStatus() != null) ? cartaoEntity.getStatus() : CardStatus.ATIVO );
            cartaoEntity.setCreatedAt(cartao.getCreatedAt());
            cartaoEntity = cardRepository.save(cartaoEntity);
            return mapper.map(cartaoEntity, CardModel.class);
        }
        throw new CardModelException(CardErrors.NOT_FOUND);
    }

    public String deleteCartaoById( Long id ) {
        while ( cardRepository.existsById( id ) ) {
            cardRepository.deleteById( id );
            return "Cartão excluído com sucesso.";
        }
        throw new CardModelException(CardErrors.NOT_FOUND);
    }

    public boolean isCardExist( CardEntity cardEntity) {
        return cardRepository.findByNumeroCartao( cardEntity.getNumeroCartao() ).isPresent();
    }

}