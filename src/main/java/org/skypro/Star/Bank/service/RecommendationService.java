package org.skypro.Star.Bank.service;

import org.skypro.Star.Bank.dynamic.DynamicRule;
import org.skypro.Star.Bank.model.DTO;
import org.skypro.Star.Bank.model.RuleStat;
import org.skypro.Star.Bank.repository.RuleStatisticRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Сервис для генерации персонализированных рекомендаций банковских продуктов.
 */
@Service
public class RecommendationService {

    private final RuleStatisticRepository statisticRepository;

    public RecommendationService(RuleStatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
    }

    /**
     * Получает рекомендации для указанного пользователя
     *
     * @param userId UUID пользователя в строковом формате
     * @return Список рекомендаций
     */
    public List<DTO> getRecommendations(String userId) {
        List<DTO> recommendations = new ArrayList<>();
        staticRules.forEach(rule -> rule.apply(userId).ifPresent(recommendations::add));
        DynamicRuleService dynamicRuleService;
        dynamicRuleService.getAllRules().data().forEach(rule -> {
            if (ruleInterpreter.evaluate(userId, rule.rule())) {
                recommendations.add(new DTO(rule.productId().toString(), rule.productName(), rule.productText()));
                updateRuleStatistic(rule.id());
            }
        });
        return recommendations;
    }

    private void updateRuleStatistic(UUID ruleId) {
        DynamicRule rule = dynamicRuleService.getRuleById(ruleId).orElseThrow(() -> new IllegalArgumentException(" Rule not found "));
        RuleStat statistic = statisticRepository.findByRule(rule).orElseGet(() -> {
            RuleStat newStat = new RuleStat();
            newStat.setRule(rule);
            return newStat;
        });
        statistic.setCount(statistic.getCount() + 1);
        statisticRepository.save(statistic);

    }

}