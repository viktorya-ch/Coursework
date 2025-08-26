package org.skypro.Star.Bank.rule;

import org.skypro.Star.Bank.model.DTO;

import java.util.Optional;

public interface RecommendationRule {
    Optional<DTO> apply (String userId);
}
