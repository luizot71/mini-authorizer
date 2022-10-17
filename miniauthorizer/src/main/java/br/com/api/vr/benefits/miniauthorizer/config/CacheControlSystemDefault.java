package br.com.api.vr.benefits.miniauthorizer.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CacheControlSystemDefault {

    public static final String CACHE_CONTROLE_SISTEMA_STATUS_GAUGE = "cache.control.systema.status.gauge";
    public static final String CACHE_CONTROLE_SISTEMA_LIMPEZA_GAUGE = "cache.control.system.clean.gauge";

    private boolean isCacheAllowed;
    private final AtomicInteger cacheEmUsoGauge;
    private final AtomicInteger cleanCacheGauge;
    private final int maxSize;
    private final List<String> cacheNames;

    public CacheControlSystemDefault(final MeterRegistry registry,
                                       final int maxSize,
                                       final List<String> cacheNames) {
        this.cacheEmUsoGauge = registry.gauge(CACHE_CONTROLE_SISTEMA_STATUS_GAUGE, new AtomicInteger(0));
        this.cleanCacheGauge = registry.gauge(CACHE_CONTROLE_SISTEMA_LIMPEZA_GAUGE, new AtomicInteger(0));
        this.maxSize = maxSize;
        this.cacheNames = cacheNames;
        suspendUseCache();
    }

    public CacheManager mountCacheManager() {
        Caffeine<Object, Object> caffeineBuilder = Caffeine.newBuilder().maximumSize(maxSize).recordStats();
        CaffeineCacheManager manager = new CaffeineCacheManager();
        manager.setCaffeine(caffeineBuilder);
        manager.setCacheNames(cacheNames);
        return manager;
    }

    public boolean isCacheAllowed() {
        return isCacheAllowed;
    }

    public void suspendUseCache() {
        isCacheAllowed = false;
        cacheEmUsoGauge.set(cacheInUseStatus());
    }

    public void allowUseCache() {
        isCacheAllowed = true;
        cacheEmUsoGauge.set(cacheInUseStatus());
    }

    public void cleanCache() {
        cleanCacheGauge.set(1);
    }

    private Integer cacheInUseStatus() {
        return isCacheAllowed ? 1 : 0;
    }
}
