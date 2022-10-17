package br.com.api.vr.benefits.miniauthorizer.repository;

import br.com.api.vr.benefits.miniauthorizer.entity.BenefitsCardEntity;
import br.com.api.vr.benefits.miniauthorizer.enums.BenefitsCardStatus;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import static br.com.api.vr.benefits.miniauthorizer.config.CacheControlSystemConfig.*;

@Repository
public interface BenefitsCardRepository extends CrudRepository<BenefitsCardEntity, Long > {

    @Cacheable(cacheNames = FIND_BENEFITS_CARD,
            cacheManager = "controlSystemCacheManager",
            condition = "@cacheControlSystemConfig.isCacheAllowed()"
    )
    Optional<BenefitsCardEntity> findBenefitsCardByNumber(String cardNumber, String token);

    BenefitsCardEntity save(BenefitsCardEntity benefitsCardEntity, String token);

    @Cacheable(cacheNames = FIND_BY_ID,
            cacheManager = "controlSystemCacheManager",
            condition = "@cacheControlSystemConfig.isCacheAllowed()"
    )
    Optional<BenefitsCardEntity> findById(Long cardId, String token);

    @Cacheable(cacheNames = FIND_ALL_BENEFITS_CARD,
            cacheManager = "controlSystemCacheManager",
            condition = "@cacheControlSystemConfig.isCacheAllowed()"
    )
    List<BenefitsCardEntity> findAllBenefitsCardsAsc(BenefitsCardStatus benefitsCardStatus);

    void deleteById(Long id, String token);

    boolean existsById(Long id, String token);

    @Cacheable(cacheNames = FIND_BENEFITS_CARD_BY_CARD_NUMBER,
            cacheManager = "controlSystemCacheManager",
            condition = "@cacheControlSystemConfig.isCacheAllowed()"
    )
    Optional<BenefitsCardEntity> findByCardNumber(String cardNumber, final String token);

    @Cacheable(cacheNames = FIND_BENEFITS_CARD_STATUS,
            cacheManager = "controlSystemCacheManager",
            condition = "@cacheControlSystemConfig.isCacheAllowed()"
    )
    List<BenefitsCardEntity> findBenefitsCardByStatusCardOrderByAsc(BenefitsCardStatus status, final String token);
}
