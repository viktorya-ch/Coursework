package org.skypro.Star.Bank.rule;

import org.skypro.Star.Bank.model.DTO;
import org.skypro.Star.Bank.repository.UserProductRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component

public class SimpleCreditRule implements RecommendationRule {
    private static final String PRODUCT_ID = "ab138afb-f3ba-4a93-b74f-0fcee86d447f";
    private static final String PRODUCT_NAME = "Простой кредит";
    private static final String DESCRIPTION = "Откройте мир выгодных кредитов...";

    private final UserProductRepository repository;

    public SimpleCreditRule(UserProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<DTO> apply(String userId) {
        boolean hasCredit = repository.hasProductType(userId, ProductType.CREDIT);
        if (hasCredit) return Optional.empty();

        BigDecimal debitDeposits = repository.getSumByProductTypeAndOperation(
                userId, ProductType.DEBIT, TransactionType.DEPOSIT);
        BigDecimal debitWithdrawals = repository.getSumByProductTypeAndOperation(
                userId, ProductType.DEBIT, TransactionType.WITHDRAWAL);

        boolean depositsExceedWithdrawals = debitDeposits.compareTo(debitWithdrawals) > 0;
        boolean withdrawalsOverThreshold = debitWithdrawals.compareTo(BigDecimal.valueOf(100_000)) > 0;

        return (depositsExceedWithdrawals && withdrawalsOverThreshold)
                ? Optional.of(new DTO(PRODUCT_ID, PRODUCT_NAME, DESCRIPTION))
                : Optional.empty();
    }
}
