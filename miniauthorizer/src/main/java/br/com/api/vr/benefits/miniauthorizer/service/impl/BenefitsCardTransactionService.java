package br.com.api.vr.benefits.miniauthorizer.service.impl;

import br.com.api.vr.benefits.miniauthorizer.entity.BenefitsCardBalanceEntity;
import br.com.api.vr.benefits.miniauthorizer.entity.BenefitsCardEntity;
import br.com.api.vr.benefits.miniauthorizer.entity.BenefitsCardTransactionEntity;
import br.com.api.vr.benefits.miniauthorizer.exception.handler.ApplicationException;
import br.com.api.vr.benefits.miniauthorizer.model.BenefitsCardCreateTransactionModel;
import br.com.api.vr.benefits.miniauthorizer.repository.BenefitsCardBalanceRepository;
import br.com.api.vr.benefits.miniauthorizer.repository.BenefitsCardRepository;
import br.com.api.vr.benefits.miniauthorizer.repository.BenefitsCardTransactionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static br.com.api.vr.benefits.miniauthorizer.enums.BenefitsCardStatus.ACTIVE_BENEFITS_CARD;
import static br.com.api.vr.benefits.miniauthorizer.model.errors.BenefitsCardErrors.*;

public class BenefitsCardTransactionService {

    @Autowired
    private BenefitsCardRepository benefitsCardRepository;

    private BenefitsCardTransactionRepository benefitsCardTransactionRepository;

    @Autowired
    private BenefitsCardBalanceRepository benefitsCardBalanceRepository;

    private ModelMapper mapper = new ModelMapper();

    public String save(BenefitsCardCreateTransactionModel benefitsCardCreateTransactionModel, final String token) throws ApplicationException {

        Optional<BenefitsCardEntity> benefitsCardEntity = benefitsCardRepository.findBenefitsCardByNumber(benefitsCardCreateTransactionModel.getCardNumber(), token);

        while (!benefitsCardEntity.isEmpty()) {
            while (benefitsCardEntity.get().getBenefitsCardStatus().equals(ACTIVE_BENEFITS_CARD)) {

                while (benefitsCardEntity.get().getPassword().equals(benefitsCardCreateTransactionModel.getPassword())) {

                    while (benefitsCardEntity.get().getBalance().getBalance().compareTo(benefitsCardCreateTransactionModel.getBalance()) >= 0) {
                        updateBenefitsCardBalance(benefitsCardEntity, benefitsCardCreateTransactionModel.getBalance(), "discount");
                        BenefitsCardTransactionEntity benefitsCardTransactionEntity = mapper.map(benefitsCardCreateTransactionModel, BenefitsCardTransactionEntity.class);
                        benefitsCardTransactionEntity.setCardNumber(benefitsCardEntity.get());
                        benefitsCardTransactionRepository.save(benefitsCardTransactionEntity);
                        return "OK";
                    }
                    throw new ApplicationException(BENEFITS_CARD_INSUFFICIENT_BALANCE);
                }
                throw new ApplicationException(BENEFITS_CARD_INVALID_PASSWORD);
            }
            throw new ApplicationException(BENEFITS_CARD_INATIVE_CARD);
        }
        throw new ApplicationException(BENEFITS_CARD_INVALID_NUMBER_CARD);
    }

    public BenefitsCardBalanceEntity updateBenefitsCardBalance(Optional<BenefitsCardEntity> cardEntity, BigDecimal value, String typeTransaction) {

        Optional<BenefitsCardBalanceEntity> benefitsCardBalanceEntity = benefitsCardBalanceRepository.findById(cardEntity.get().getCardId());

        BigDecimal newValue = (typeTransaction.equals("discount")) ? benefitsCardBalanceEntity.get().getBalance().subtract(value) : benefitsCardBalanceEntity.get().getBalance().add(value);

        benefitsCardBalanceEntity.get().setBalance(newValue);

        return benefitsCardBalanceRepository.save(benefitsCardBalanceEntity.get());
    }

    public String deleteBenefitsCardById(Long cardId, final String token) throws ApplicationException {

        Optional<BenefitsCardTransactionEntity> benefitsCardTransactionEntity = benefitsCardTransactionRepository.findById(cardId);

        while ( benefitsCardTransactionEntity.isPresent() ) {
            Optional<BenefitsCardEntity> cartao = benefitsCardRepository.findBenefitsCardByNumber(benefitsCardTransactionEntity.get().getCardNumber().getCardNumber(), token);

            benefitsCardTransactionRepository.deleteById(cardId);

            updateBenefitsCardBalance(cartao, benefitsCardTransactionEntity.get().getBalance(), "credit");

            return "Transaction deleted successfully.";
        }
        throw new ApplicationException(BENEFITS_CARD_NOT_FOUND);
    }

    public BenefitsCardCreateTransactionModel findById(Long cardId, final String token) throws ApplicationException {
        BenefitsCardTransactionEntity transacao = benefitsCardTransactionRepository.findById(cardId, token).orElse( new BenefitsCardTransactionEntity() );
        while ( transacao.getCardId() == null ) {
            throw new ApplicationException(BENEFITS_CARD_NOT_FOUND);
        }
        return mapper.map(transacao, BenefitsCardCreateTransactionModel.class);
    }

    public List<BenefitsCardCreateTransactionModel> findAll() throws ApplicationException {

        List<BenefitsCardTransactionEntity> transacoes = benefitsCardTransactionRepository.findAll();

        while ( transacoes.isEmpty() ) {
            throw new ApplicationException(BENEFITS_CARD_NOT_FOUND);
        }

        return transacoes.stream().map(entity -> mapper.map(entity, BenefitsCardCreateTransactionModel.class)).collect(Collectors.toList());
    }
}
