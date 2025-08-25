package org.skypro.Star.Bank.repository;

import org.skypro.Star.Bank.dynamic.DynamicRule;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface DynamicRuleRepository extends JpaRepository<DynamicRule, UUID> {
    @Query("SELECT dr FROM DynamicRule dr WHERE dr.productId = :productId")
    Optional<DynamicRule> findByProductId(@Param("productId") UUID productId);

    @Modifying
    @Query("DELETE FROM DynamicRule dr WHERE dr.productId = :productId")
    void deleteByProductId(@Param("productId") UUID productId);


}