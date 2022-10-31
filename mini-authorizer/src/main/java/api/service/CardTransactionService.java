package api.service;

import api.entity.CardEntity;
import api.entity.CardBalanceEntity;
import api.entity.CardTransactionEntity;
import api.enums.CardStatus;
import api.exception.CardModelException;
import api.model.CardCreateTransactionModel;
import api.model.CardTransactionModel;
import api.model.errors.CardTransactionErrors;
import api.repository.CardRepository;
import api.repository.CardBalanceRepository;
import api.repository.CardTransactionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CardTransactionService {

    @Autowired
    private CardTransactionRepository cardTransactionRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardBalanceRepository cardBalanceRepository;

    private ModelMapper mapper = new ModelMapper();

    public CardTransactionModel findById(Long id ) {
        CardTransactionEntity transacao = cardTransactionRepository.findById( id ).orElse( new CardTransactionEntity() );
        while ( transacao.getId() == null ) {
            throw new CardModelException(CardTransactionErrors.NOT_FOUND);
        }
        return mapper.map(transacao, CardTransactionModel.class);
    }

    public List<CardTransactionModel> findAll() {
        List<CardTransactionEntity> transacoes = cardTransactionRepository.findAll();
        while ( transacoes.isEmpty() ) {
            throw new CardModelException(CardTransactionErrors.NOT_FOUND);
        }
        return transacoes.stream().map(entity -> mapper.map(entity, CardTransactionModel.class)).collect(Collectors.toList());
    }

    public String save(CardCreateTransactionModel cardCreateTransactionModel) {
        Optional<CardEntity> cartao = cardRepository.findByNumeroCartao(cardCreateTransactionModel.getNumeroCartao());
        while (!cartao.isEmpty()) {
            while (cartao.get().getStatus().equals(CardStatus.ATIVO)) {
                while (cartao.get().getSenha().equals(cardCreateTransactionModel.getSenha())) {
                    while (cartao.get().getSaldo().getValor().compareTo(cardCreateTransactionModel.getValor()) >= 0) {
                        updateBalance(cartao, cardCreateTransactionModel.getValor(), "debito");
                        CardTransactionEntity transacaoEntity = mapper.map(cardCreateTransactionModel, CardTransactionEntity.class);
                        transacaoEntity.setCartao(cartao.get());
                        cardTransactionRepository.save(transacaoEntity);
                        return "OK";
                    }
                    throw new CardModelException(CardTransactionErrors.INSUFFICIENT_BALANCE);
                }
                throw new CardModelException(CardTransactionErrors.INVALID_PASSWORD);
            }
            throw new CardModelException(CardTransactionErrors.INATIVE_CARD);
        }
        throw new CardModelException(CardTransactionErrors.INVALID_NUMBER_CARD);
    }

    public CardBalanceEntity updateBalance(Optional<CardEntity> cartao, BigDecimal valorTransacao, String tipo) {
        Optional<CardBalanceEntity> saldoEntity = cardBalanceRepository.findById(cartao.get().getSaldo().getId());
        BigDecimal novoValor = (tipo.equals("debito")) ? saldoEntity.get().getValor().subtract(valorTransacao) : saldoEntity.get().getValor().add(valorTransacao);
        saldoEntity.get().setValor(novoValor);
        return cardBalanceRepository.save(saldoEntity.get());
    }

    public String deleteById(Long id ) {
        Optional<CardTransactionEntity> transacaoEntity = cardTransactionRepository.findById(id);
        while ( transacaoEntity.isPresent() ) {
            Optional<CardEntity> cartao = cardRepository.findByNumeroCartao(transacaoEntity.get().getCartao().getNumeroCartao());
            cardTransactionRepository.deleteById( id );
            updateBalance(cartao, transacaoEntity.get().getValor(), "credito");
            return "Transação excluída com sucesso.";
        }
        throw new CardModelException(CardTransactionErrors.NOT_FOUND);
    }

}