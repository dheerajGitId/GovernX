package com.clientdata.dashboardservice.service;

import com.github.benmanes.caffeine.cache.stats.CacheStats;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Service;

import static com.clientdata.dashboardservice.configuration.CacheConfig.POLICY_DOCUMENT_CACHE;

@Service
@Slf4j
@AllArgsConstructor
public class CacheMonitoringService {

    private CacheManager cacheManager;

    public void logPolicyAuditCacheStats() {

        CaffeineCache cache = (CaffeineCache) cacheManager.getCache(POLICY_DOCUMENT_CACHE);

        if (cache == null) {
            log.info("Cache not found: policyAudits");
            return;
        }

        CacheStats stats = cache.getNativeCache().stats();

        log.info("Cache Stats - Hit: {}, Miss: {}, HitRate: {}",
                stats.hitCount(),
                stats.missCount(),
                stats.hitRate());
    }
}