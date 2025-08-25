package org.skypro.Star.Bank.repository;

import org.skypro.Star.Bank.dynamic.DynamicRule;
import org.skypro.Star.Bank.model.RuleStat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RuleStatisticRepository extends JpaRepository <RuleStat, UUID> {
    Optional<RuleStat> findByRule(DynamicRule rule);
}
