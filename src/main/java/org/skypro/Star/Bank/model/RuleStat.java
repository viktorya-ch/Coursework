package org.skypro.Star.Bank.model;

import jakarta.persistence.*;
import org.skypro.Star.Bank.dynamic.DynamicRule;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "rule_stat")
public class RuleStat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "rule_id", referencedColumnName = "id")
    private DynamicRule rule;

    @Column(nullable = false)
    private Long count = 0L;


    public RuleStat(UUID id, DynamicRule rule, Long count) {
        this.id = id;
        this.rule = rule;
        this.count = count;
    }

    public UUID getId() {
        return id;
    }

    public DynamicRule getRule() {
        return rule;
    }

    public Long getCount() {
        return count;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setRule(DynamicRule rule) {
        this.rule = rule;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RuleStat ruleStat = (RuleStat) o;
        return Objects.equals(id, ruleStat.id) && Objects.equals(rule, ruleStat.rule) && Objects.equals(count, ruleStat.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rule, count);
    }

    @Override
    public String toString() {
        return "RuleStat{" +
                "id=" + id +
                ", rule=" + rule +
                ", count=" + count +
                '}';
    }
}
