package org.skypro.Star.Bank.model;

import org.skypro.Star.Bank.dynamic.RuleCondition;

import java.util.List;
import java.util.UUID;

public record DynamicRuleResponse (
        UUID id,
        String productName,
        UUID productId,
        String productText,
        List<RuleCondition> rule
) {}
