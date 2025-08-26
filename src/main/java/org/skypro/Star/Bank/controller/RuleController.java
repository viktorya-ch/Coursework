package org.skypro.Star.Bank.controller;

import jakarta.validation.Valid;
import org.skypro.Star.Bank.model.DynamicRuleRequest;
import org.skypro.Star.Bank.model.DynamicRuleResponse;
import org.skypro.Star.Bank.model.RuleListResponse;
import org.skypro.Star.Bank.model.RuleStatsResponse;
import org.skypro.Star.Bank.service.DynamicRuleService;
import org.skypro.Star.Bank.service.RuleStatisticService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(" /rule ")
public class RuleController {

    private final RuleStatisticService statisticService;
    private final DynamicRuleService dynamicRuleService;

    public RuleController(RuleStatisticService statisticService, DynamicRuleService dynamicRuleService) {
        this.statisticService = statisticService;
        this.dynamicRuleService = dynamicRuleService;
    }

    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.CREATED)
    public RuleStatsResponse getStats() {
        return statisticService.getStatistics();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DynamicRuleResponse createRule(@RequestBody @Valid DynamicRuleRequest request) {
        return dynamicRuleService.createRule(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RuleListResponse getAllRules() {
        return dynamicRuleService.getAllRules();
    }

    @DeleteMapping(" /{id} ")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRule(@PathVariable UUID id) {
        dynamicRuleService.deleteRule(id);
    }

}
