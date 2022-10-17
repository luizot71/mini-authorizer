package br.com.api.vr.benefits.miniauthorizer.service.impl;

import br.com.api.vr.benefits.miniauthorizer.entity.BenefitsCardEntity;
import br.com.api.vr.benefits.miniauthorizer.exception.handler.ApplicationException;
import br.com.api.vr.benefits.miniauthorizer.model.BenefitsCardCreateModel;
import br.com.api.vr.benefits.miniauthorizer.model.BenefitsCardModel;
import br.com.api.vr.benefits.miniauthorizer.repository.BenefitsCardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import javax.validation.Valid;
import static br.com.api.vr.benefits.miniauthorizer.model.errors.BenefitsCardErrors.BENEFITS_CARD_ERROR_CREATING;
import static br.com.api.vr.benefits.miniauthorizer.model.errors.BenefitsCardErrors.BENEFITS_CARD_EXISTS;

@Service
@Transactional
public class BenefitsCardService {

    @Autowired
    private BenefitsCardRepository cartaoRepository;

    private ModelMapper mapper = new ModelMapper();

    public BenefitsCardModel save(@Valid BenefitsCardCreateModel benefitsCardCreateModel, final String token) throws ApplicationException {

        BenefitsCardEntity benefitsCardEntity = mapper.map(benefitsCardCreateModel, BenefitsCardEntity.class);

        while ( isBenefitsCardExists(benefitsCardEntity) ) {
            throw new ApplicationException(BENEFITS_CARD_EXISTS);
        }

        try {

            benefitsCardEntity = cartaoRepository.save(benefitsCardEntity);

            return mapper.map(benefitsCardEntity, BenefitsCardModel.class);

        } catch (Exception e) {
            throw new ApplicationException(BENEFITS_CARD_ERROR_CREATING);
        }
    }

    public boolean isBenefitsCardExists(BenefitsCardEntity benefitsCardEntity) {
        return cartaoRepository.findBenefitsCardByNumber( benefitsCardEntity.getCardNumber() ).isPresent();
    }
}
