package org.skypro.Star.Bank;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.Star.Bank.dynamic.RuleCondition;
import org.skypro.Star.Bank.repository.CachedUserRepository;
import org.skypro.Star.Bank.service.RuleInterpreter;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RuleInterpreterTest {

    @Mock
    private CachedUserRepository cachedUserRepository;

    @InjectMocks
    private RuleInterpreter ruleInterpreter;

    @Test
    void evaluate_ShouldReturnTrueWhenAllConditionsMet() {
        RuleCondition condition1 = new RuleCondition();
        condition1.setQuery("USER_OF");
        condition1.setArguments(List.of("DEBIT"));
        condition1.setNegate(false);

        RuleCondition condition2 = new RuleCondition();
        condition2.setQuery("TRANSACTION_SUM_COMPARE");
        condition2.setArguments(List.of("DEBIT", "DEPOSIT", ">", "1000"));
        condition2.setNegate(false);

        when(cachedUserRepository.hasProductType(anyString(), any())).thenReturn(true);
        when(cachedUserRepository.getSumByProductTypeAndOperation(anyString(), any(), any()))
                .thenReturn(BigDecimal.valueOf(2000));

        boolean result = ruleInterpreter.evaluate("test-user", List.of(condition1, condition2));

        assertThat(result).isTrue();
    }

    @Test
    void evaluate_ShouldReturnFalseWhenConditionFails() {
        RuleCondition condition = new RuleCondition();
        condition.setQuery("USER_OF");
        condition.setArguments(List.of("DEBIT"));
        condition.setNegate(false);

        when(cachedUserRepository.hasProductType(anyString(), any())).thenReturn(false);

        boolean result = ruleInterpreter.evaluate("test-user", List.of(condition));

        assertThat(result).isFalse();
    }

    @Test
    void evaluate_ShouldHandleNegatedConditions(){
        RuleCondition condition = new RuleCondition();
        condition.setQuery(" USER_OF ");
        condition.setArguments(List.of(" DEBIT "));
        condition.setNegate(true);

        when(cachedUserRepository.hasProductType(anyString(),any())).thenReturn(true);

        boolean result = ruleInterpreter.evaluate(" test-user ", List.of(condition));
        assertThat(result).isFalse();
    }

}
