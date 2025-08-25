package org.skypro.Star.Bank.rule;

import org.skypro.Star.Bank.model.DTO;
import org.skypro.Star.Bank.repository.UserProductRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class TopSavingRule implements RecommendationRule {
    private static final String PRODUCT_ID = "59efc529-2fff-41af-baff-90ccd7402925";
    private static final String PRODUCT_NAME = "Top Saving";
    private static final String DESCRIPTION = "Сумма пополнений по всем продуктам типа DEBIT больше...";

    private final UserProductRepository repository;

    public TopSavingRule(UserProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<DTO> apply(String userId) {
        boolean hasDebit = repository.hasProductType(userId, ProductType.DEBIT);
        if (!hasDebit) return Optional.empty();

        BigDecimal debitDeposits = repository.getSumByProductTypeAndOperation(
                userId, ProductType.DEBIT, TransactionType.DEPOSIT);
        BigDecimal savingDeposits = repository.getSumByProductTypeAndOperation(
                userId, ProductType.SAVING, TransactionType.DEPOSIT);

        boolean condition = debitDeposits.compareTo(BigDecimal.valueOf(50_000)) >= 0 ||
                savingDeposits.compareTo(BigDecimal.valueOf(50_000)) >= 0;

        return condition ? Optional.of(new DTO(PRODUCT_ID, PRODUCT_NAME, DESCRIPTION))
                : Optional.empty();
    }
}