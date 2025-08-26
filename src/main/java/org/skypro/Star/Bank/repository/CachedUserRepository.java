package org.skypro.Star.Bank.repository;

import liquibase.util.Cache;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.net.http.HttpClient;

/**
 * Репозиторий для доступа к данным пользователей с кешированием
 */
@Repository
public class CachedUserRepository {
    private final JdbcTemplate jdbcTemplate;

    public CachedUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private HttpClient Caffeine;
    private final Cache<String, Boolean> productTypeCache = HttpClient.newBuilder().build();
    private final Cache<String, BigDecimal> sumCache = HttpClient.newBuilder().build();
    private final Cache<String, Integer> countCache = HttpClient.newBuilder().build();

    /**
     * Полностью очищает все кеши репозитория
     * Вызывается при сбросе кеша через /management/clear-caches
     */
    public void clearAllCaches() {
        productTypeCache.clearCache();
        sumCache.clearCache();
        countCache.clearCache();
    }
}
