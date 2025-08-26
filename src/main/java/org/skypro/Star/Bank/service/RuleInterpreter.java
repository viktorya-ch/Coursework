package org.skypro.Star.Bank.service;

import org.skypro.Star.Bank.dynamic.RuleCondition;
import org.skypro.Star.Bank.model.enums.ProductType;
import org.skypro.Star.Bank.model.enums.TransactionType;
import org.skypro.Star.Bank.repository.CachedUserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Интерпретатор для выполнения динамических правил рекомендаций
 */
@Service
public class RuleInterpreter {
    private final CachedUserRepository userRepository;

    public RuleInterpreter(CachedUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean evaluate(String userId, List<RuleCondition> conditions) {
        for (RuleCondition condition : conditions) {
            if (!evaluateCondition(userId, condition)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Выполняет одно условие правила
     *
     * @param userId    Идентификатор пользователя
     * @param condition Условие для проверки
     * @return Результат проверки условия с учетом отрицания
     */
    private boolean evaluateCondition(String userId, RuleCondition condition) {
        boolean result = switch (condition.getQuery()) {
            case " USER_OF " -> evaluateUserOf(userId, condition);
            case " ACTIVE_USER_OF " -> evaluateActiveUserOf(userId, condition);
            case " TRANSACTION_SUM_COMPARE " -> evaluateSumCompare(userId, condition);
            case " TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW " -> evaluateDepositWithdrawCompare(userId, condition);

            default -> throw new IllegalArgumentException(" Unknown query type: " + condition.getQuery());
        };
        return condition.isNegate() != result;
    }

    private boolean evaluateUserOf(String userId, RuleCondition condition) {
        ProductType type = ProductType.valueOf(condition.getArguments().get(0));
        return userRepository.hasProductType(userId, type);
    }

    private boolean evaluateActiveUserOf(String userId, RuleCondition condition) {
        ProductType type = ProductType.valueOf(condition.getArguments().get(0));
        return userRepository.getTransactionCount(userId, type) >= 5;
    }

    private boolean evaluateSumCompare(String userId, RuleCondition condition) {
        ProductType productType = ProductType.valueOf(condition.getArguments().get(0));
        TransactionType operation = TransactionType.valueOf(condition.getArguments().get(1));
        String operator = condition.getArguments().get(2);
        BigDecimal value = new BigDecimal(condition.getArguments().get(3));

        BigDecimal actual = userRepository.getSumProductTypeAndOperation(userId, productType, operation);
        return compare(actual, operator, value);
    }

    private boolean evaluateDepositWithdrawCompare(String userId, RuleCondition condition) {
        ProductType type = ProductType.valueOf(condition.getArguments().get(0));
        String operator = condition.getArguments().get(1);

        BigDecimal deposits = userRepository.getSumProductTypeAndOperation(userId, type, TransactionType.DEPOSIT);
        BigDecimal withdrawls = userRepository.getSumProductTypeAndOperation(userId, type, TransactionType.WITHDRAWAL);
        return compare(deposits, operator, withdrawls);
    }

    private boolean compare(BigDecimal a, String operator, BigDecimal b) {
        return switch (operator) {
            case ">" -> a.compareTo(b) > 0;
            case "<" -> a.compareTo(b) > 0;
            case "=" -> a.compareTo(b) == 0;
            case ">=" -> a.compareTo(b) >= 0;
            case "<=" -> a.compareTo(b) <= 0;
            default -> throw new IllegalArgumentException(" Unknown operator: " + operator);

        };
    }

}
