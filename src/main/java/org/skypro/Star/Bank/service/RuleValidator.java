package org.skypro.Star.Bank.service;

import org.skypro.Star.Bank.dynamic.RuleCondition;
import org.skypro.Star.Bank.model.enums.ProductType;
import org.skypro.Star.Bank.model.enums.TransactionType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RuleValidator {

    public void validate(List<RuleCondition> rule) {
        for (RuleCondition condition : rule) {
            validateCondition(condition);
        }
    }

    private void validateCondition(RuleCondition condition) {
        switch (condition.getQuery()) {
            case "USER_OF", "ACTIVE_USER_OF" -> validateArguments(condition, 1, ProductType.class);
            case "TRANSACTION_SUM_COMPARE" -> {
                validateArguments(condition, 4, ProductType.class, TransactionType.class);
                validateNumber(condition.getArguments().get(3));
            }
            case "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW" -> validateArguments(condition, 2, ProductType.class);

            default -> throw new IllegalArgumentException(" Недопустимый тип запроса " + condition.getQuery());
        }
    }

    private void validateArguments(RuleCondition condition, int expectedSize, Class<? extends Enum<?>>... enums) {
        if (condition.getArguments().size() != expectedSize) {
            throw new IllegalArgumentException(
                    " Недопустимые аргументы для " + condition.getQuery());
        }
        for (int i = 0; i < enums.length; i++) {
            try {
                Enum.valueOf(enums[i], condition.getArguments().get(i));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(" Недопустимые значения аргументов " + condition.getArguments().get(i));
            }
        }
    }

    private void validateNumber(String value) {
        try {
            new BigDecimal(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(" Неверный номер " + value);
        }
    }
}
