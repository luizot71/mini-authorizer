package br.com.api.vr.benefits.miniauthorizer.service.impl;

import br.com.api.vr.benefits.miniauthorizer.entity.BenefitsCardBalanceEntity;
import br.com.api.vr.benefits.miniauthorizer.entity.BenefitsCardEntity;
import br.com.api.vr.benefits.miniauthorizer.exception.handler.ApplicationException;
import br.com.api.vr.benefits.miniauthorizer.model.BenefitsCardCreateModel;
import br.com.api.vr.benefits.miniauthorizer.model.BenefitsCardModel;
import br.com.api.vr.benefits.miniauthorizer.repository.BenefitsCardBalanceRepository;
import br.com.api.vr.benefits.miniauthorizer.repository.BenefitsCardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import javax.validation.Valid;
import static br.com.api.vr.benefits.miniauthorizer.enums.BenefitsCardStatus.ACTIVE_BENEFITS_CARD;
import static br.com.api.vr.benefits.miniauthorizer.model.errors.BenefitsCardErrors.*;

@Service
@Transactional
public class BenefitsCardService {

    @Autowired
    private BenefitsCardRepository benefitsCardRepository;

    @Autowired
    private BenefitsCardBalanceRepository benefitCardBalanceRepository;

    private ModelMapper mapper = new ModelMapper();

    public BenefitsCardModel save(@Valid BenefitsCardCreateModel benefitsCardCreateModel, final String token) throws ApplicationException {

        BenefitsCardEntity benefitsCardEntity = mapper.map(benefitsCardCreateModel, BenefitsCardEntity.class);

        while ( isBenefitsCardExists(benefitsCardEntity) ) {
            throw new ApplicationException(BENEFITS_CARD_EXISTS);
        }

        try {

            BenefitsCardBalanceEntity benefitsCardBalanceEntity = new BenefitsCardBalanceEntity();
            benefitCardBalanceRepository.save(benefitsCardBalanceEntity);
            benefitsCardEntity.setBalance(benefitsCardBalanceEntity);
            benefitsCardEntity.setBenefitsCardStatus(ACTIVE_BENEFITS_CARD);

            benefitsCardEntity = benefitsCardRepository.save(benefitsCardEntity, token);

            return mapper.map(benefitsCardEntity, BenefitsCardModel.class);

        } catch (Exception e) {
            throw new ApplicationException(BENEFITS_CARD_ERROR_CREATING);
        }
    }

    @Transactional
    public BenefitsCardModel updateCard(Long cardId, BenefitsCardEntity entity, final String token) throws ApplicationException {

        BenefitsCardEntity benefitsCardEntity = benefitsCardRepository.findById( cardId ).orElse( new BenefitsCardEntity() );

        while (benefitsCardRepository.existsById(cardId)) {

            entity.setCardId(cardId);

            BenefitsCardBalanceEntity benefitsCardBalanceEntity = (benefitsCardEntity.getBalance() != null) ? benefitCardBalanceRepository.findById( benefitsCardEntity.getBalance().getCardId() ).orElse( new BenefitsCardBalanceEntity() ) : new BenefitsCardBalanceEntity();
            entity.setBalance(benefitsCardBalanceEntity);
            entity.setCardNumber( entity.getCardNumber() );
            entity.setPassword( entity.getPassword() );
            entity.setBenefitsCardStatus( (entity.getBenefitsCardStatus() != null) ? entity.getBenefitsCardStatus() : ACTIVE_BENEFITS_CARD );
            entity.setCreatedAt(entity.getCreatedAt());

            entity = benefitsCardRepository.save(entity, token);

            return mapper.map(entity, BenefitsCardModel.class);
        }
        throw new ApplicationException(BENEFITS_CARD_NOT_FOUND);
    }

    public boolean isBenefitsCardExists(BenefitsCardEntity benefitsCardEntity) {
        return benefitsCardRepository.findBenefitsCardByNumber( benefitsCardEntity.getCardNumber() ).isPresent();
    }
}
