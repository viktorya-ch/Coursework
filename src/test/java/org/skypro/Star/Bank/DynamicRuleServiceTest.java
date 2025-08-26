package org.skypro.Star.Bank;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.Star.Bank.dynamic.DynamicRule;
import org.skypro.Star.Bank.dynamic.RuleCondition;
import org.skypro.Star.Bank.model.DynamicRuleRequest;
import org.skypro.Star.Bank.model.DynamicRuleResponse;
import org.skypro.Star.Bank.model.RuleListResponse;
import org.skypro.Star.Bank.repository.DynamicRuleRepository;
import org.skypro.Star.Bank.service.DynamicRuleService;
import org.skypro.Star.Bank.service.RuleValidator;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DynamicRuleServiceTest {

    @Mock
    private DynamicRuleRepository dynamicRuleRepository;

    @Mock
    private RuleValidator ruleValidator;

    @InjectMocks
    private DynamicRuleService dynamicRuleService;

    @Test
    void createRule_ShouldSaveAndReturnRule() {
        DynamicRuleRequest request = new DynamicRuleRequest(
                "Test Product", UUID.randomUUID(), "Test description",
                List.of(new RuleCondition())
        );

        DynamicRule savedRule = new DynamicRule();
        savedRule.setId(UUID.randomUUID());
        savedRule.setProductName(request.productName());
        savedRule.setProductId(request.productId());
        savedRule.setProductText(request.productText());
        savedRule.setRule(request.rule());

        when(dynamicRuleRepository.save(any())).thenReturn(savedRule);

        DynamicRuleResponse response = dynamicRuleService.createRule(request);

        assertThat(response.productName()).isEqualTo("Test Product");
        verify(ruleValidator, times(1)).validate(anyList());
        verify(dynamicRuleRepository, times(1)).save(any());
    }

    @Test
    void getAllRules_ShouldReturnAllRules(){
        DynamicRule rule = new DynamicRule();
        rule.setId(UUID.randomUUID());
        rule.setProductName(" Test Product ");
        when(dynamicRuleRepository.findAll()).thenReturn(List.of(rule));
        RuleListResponse response = dynamicRuleService.getAllRules();

        assertThat(response.data()).hasSize(1);
        assertThat(response.data().get(0).productName()).isEqualTo(" Test Product ");
    }

    @Test
    void deleteRule_ShouldCallRepository(){
        UUID ruleId = UUID.randomUUID();
        doNothing().when(dynamicRuleRepository).deleteById(ruleId);

        assertDoesNotThrow(() -> dynamicRuleService.deleteRule(ruleId));
        verify(dynamicRuleRepository, times(1)).deleteById(ruleId);
    }
}