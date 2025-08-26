package org.skypro.Star.Bank.repository;

import org.skypro.Star.Bank.model.enums.ProductType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class UserProductRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean isUserUsingProductType(String userId, String productType) {
        String sql = "SELECT COUNT(*) > 0 FROM TRANSACTION t" + "JOIN PRODUCT p ON t.PRODUCT_ID = p.ID " +
                "WHERE t.USER_ID = ? AND p.TYPE = ?";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, userId, productType));
    }

    public BigDecimal getTotalAmountForProductTypeAndTransactionType(String userId, String productType, String
            transactionType) {
        String sql = "SELECT COALESCE(SUM(t.AMOUNT), 0) FROM TRANSACTION t" +
                "JOIN PRODUCT p ON t.PRODUCT_ID = p.ID " +
                "WHERE t.USER_ID = ? AND p.TYPE = ? AND t.TYPE = ?";
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, userId, productType, transactionType);
    }

    public BigDecimal getTotalDepositForProductType(String userId, String productType) {
        return getTotalAmountForProductTypeAndTransactionType(userId, productType, "DEPOSIT");
    }

    public BigDecimal getTotalWithdrawalForProductType(String userId, String productType) {
        return getTotalAmountForProductTypeAndTransactionType(userId, productType, "WITHDRAWAL");
    }

}

