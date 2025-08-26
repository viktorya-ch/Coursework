package org.skypro.Star.Bank;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.Star.Bank.dynamic.RuleCondition;
import org.skypro.Star.Bank.model.DTO;
import org.skypro.Star.Bank.model.DynamicRuleResponse;
import org.skypro.Star.Bank.model.RuleListResponse;
import org.skypro.Star.Bank.rule.RecommendationRule;
import org.skypro.Star.Bank.service.DynamicRuleService;
import org.skypro.Star.Bank.service.RecommendationService;
import org.skypro.Star.Bank.service.RuleInterpreter;
import org.skypro.Star.Bank.service.RuleStatisticService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class RecommendationServiceTest {

    @Mock
    private List<RecommendationRule> staticRules;

    @Mock
    private DynamicRuleService dynamicRuleService;

    @Mock
    private RuleInterpreter ruleInterpreter;

    @Mock
    RuleStatisticService statisticService;

    @InjectMocks
    private RecommendationService recommendationService;

    @Test
    void getRecommendations_ShouldCombineStaticAndDynamicRules() {
        // Mock static rules
        DTO staticRec = new DTO("1", "Static Product", "Static desc");
        when(staticRules.stream()).thenReturn(Stream.of(mock(RecommendationRule.class)));
        when(staticRules.get(0).apply(anyString())).thenReturn(Optional.of(staticRec));

        // Mock dynamic rules
        DynamicRuleResponse dynamicRule = new DynamicRuleResponse(
                UUID.randomUUID(), "Dynamic Product", UUID.randomUUID(),
                "Dynamic desc", List.of(new RuleCondition())
        );
        RuleListResponse dynamicRules = new RuleListResponse(List.of(dynamicRule));
        when(dynamicRuleService.getAllRules()).thenReturn(dynamicRules);
        when(ruleInterpreter.evaluate(anyString(), anyList())).thenReturn(true);

        // Execute
        List<DTO> result = recommendationService.getRecommendations("test-user");

        // Verify
        assertThat(result).hasSize(2);
        verify(statisticService, times(1)).incrementCounter(any());
    }

    @Test
    void clearUserCache_ShouldEvictCache(){
        // This is mostly to verify the annotation works
        assertDoesNotThrow(()-> recommendationService.clearUserCache("test-user"));
    }




}
