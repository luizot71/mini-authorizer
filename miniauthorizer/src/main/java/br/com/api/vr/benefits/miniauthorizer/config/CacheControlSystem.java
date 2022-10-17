package br.com.api.vr.benefits.miniauthorizer.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.cache.CacheManager;

public interface CacheControlSystem {

    CacheManager controlSystemCacheManager(MeterRegistry registry);
}
