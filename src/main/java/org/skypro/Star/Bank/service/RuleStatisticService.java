package org.skypro.Star.Bank.service;


import org.skypro.Star.Bank.model.RuleStat;
import org.skypro.Star.Bank.model.RuleStatDTO;
import org.skypro.Star.Bank.model.RuleStatsResponse;
import org.skypro.Star.Bank.repository.RuleStatisticRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleStatisticService {

    private final RuleStatisticRepository repository;
    private final DynamicRuleService dynamicRuleService;


    public RuleStatisticService(RuleStatisticRepository repository, DynamicRuleService dynamicRuleService) {
        this.repository = repository;
        this.dynamicRuleService = dynamicRuleService;
    }

    public RuleStatsResponse getStatistics() {
        List<RuleStat> stats = repository.findAll();
        List<RuleStatDTO> statDTOs = stats.stream()
                .map(stat -> new RuleStatDTO(
                        stat.getRule().getId(),
                        stat.getCount()
                ))
                .toList();


        List<RuleStatDTO> allStats = dynamicRuleService.getAllRules().data().stream()
                .map(rule -> new RuleStatDTO(
                        rule.id(),
                        stats.stream()
                                .filter(s -> s.getRule().getId().equals(rule.id()))
                                .findFirst()
                                .map(RuleStat::getCount)
                                .orElse(0L)
                ))
                .toList();

        return new RuleStatsResponse(allStats);
    }
}
