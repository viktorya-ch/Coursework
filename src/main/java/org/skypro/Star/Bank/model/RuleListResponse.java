package org.skypro.Star.Bank.model;

import java.util.List;

public record RuleListResponse (
        List<DynamicRuleResponse> data
) {}

