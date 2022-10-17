package br.com.api.vr.benefits.miniauthorizer.config;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Data
@Profile("!integration_test")
@Configuration
@ConfigurationProperties(prefix = "application.cache.system-control")
public class CacheControlSystemConfig implements CacheControlSystem {

    public static final String CRON_SUSPEND_CACHE = "${application.cache.system-control.suspend-cache-use.cron:00 00 17 * * *}";
    public static final String CRON_ALLOW_CACHE = "${application.cache.system-control.allow-use-cache.cron:00 00 08 * * *}";
    public static final String CRON_CLEAR_CACHE = "${application.cache.system-control.evict.cron:00 01 17 * * *}";

    public static final String FIND_BENEFITS_CARD = "find-benefits-card";
    public static final String FIND_BY_ID = "find-by-id";
    public static final String FIND_ALL_BENEFITS_CARD = "find-all-benefits-card";
    public static final String FIND_BENEFITS_CARD_BY_CARD_NUMBER = "find-benefits-card-by-card-number";
    public static final String FIND_BENEFITS_CARD_STATUS = "find-benefits-card-status";
    public static final String CONTROL_SYSTEM_CACHE_MANAGER = "controleSistemaCacheManager";;

    private boolean startEnabled;
    private List<String> caches;
    private Cron suspenderUsoCache;
    private Cron permitirUsoCache;
    private Cron evict;
    private Integer maxSize;
    private CacheControlSystemDefault cacheControlSystemDefault;

   // @Bean
    @Override
    public CacheManager controlSystemCacheManager(final MeterRegistry registry) {
        this.cacheControlSystemDefault = new CacheControlSystemDefault(registry, maxSize, caches);
        if (startEnabled) {
            cacheControlSystemDefault.allowUseCache();
        }
        return cacheControlSystemDefault.mountCacheManager();
    }

    public boolean isCacheAllowed() {
        return cacheControlSystemDefault.isCacheAllowed();
    }

    @Scheduled(cron = CRON_SUSPEND_CACHE)
    public void suspendUseCache() {
        cacheControlSystemDefault.suspendUseCache();
    }

    @Scheduled(cron = CRON_ALLOW_CACHE)
    public void allowUseCache() {
        cacheControlSystemDefault.allowUseCache();
    }

    @Scheduled(cron = CRON_CLEAR_CACHE)
    @CacheEvict(
            cacheManager = CONTROL_SYSTEM_CACHE_MANAGER,
            cacheNames = {FIND_BENEFITS_CARD, FIND_BY_ID, FIND_ALL_BENEFITS_CARD, FIND_BENEFITS_CARD_BY_CARD_NUMBER, FIND_BENEFITS_CARD_STATUS},
            allEntries = true
    )
    public void cacheClean() {
        cacheControlSystemDefault.cleanCache();
    }

    @Data
    public static class Cron {
        private String cron;
    }
}
