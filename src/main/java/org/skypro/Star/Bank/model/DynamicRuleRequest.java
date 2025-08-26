package org.skypro.Star.Bank.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.skypro.Star.Bank.dynamic.RuleCondition;

import java.util.List;
import java.util.UUID;

public record DynamicRuleRequest(
        @NotBlank
        String productName,
        @NotNull
        UUID productId,
        @NotBlank String productText,
        @Valid
        @Size(min = 1)
        List<RuleCondition> rule) {
}


