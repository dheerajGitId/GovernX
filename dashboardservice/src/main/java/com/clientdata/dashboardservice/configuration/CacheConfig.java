package com.clientdata.dashboardservice.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {
    public static final String POLICY_DOCUMENT_CACHE = "policyDocumentCache";
    public static final String KEY_GENERATOR = "tenantKeyGenerator";

    @Bean
    public CaffeineCacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(POLICY_DOCUMENT_CACHE);

        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(3, TimeUnit.HOURS)
                .maximumSize(100)
                .recordStats());

        return cacheManager;
    }
    @Bean(KEY_GENERATOR)  // 🔥 NAME MUST MATCH
    public KeyGenerator tenantKeyGenerator() {
        return (target, method, params) -> {
            String tenantId = TenantContext.getTenant();
            return tenantId + "_" + method.getName();
        };
    }
}
