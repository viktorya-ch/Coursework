package org.skypro.Star.Bank.dynamic;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.OneToMany;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = " dynamic_rule")
public class DynamicRule {

    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private String productName;

    @Column
    private UUID productId;

    @Column
    private String productText;

    @OneToMany(mappedBy = " rule ", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RuleCondition> conditions = new ArrayList<>();


    public DynamicRule(UUID id, String productName, UUID productId, String productText) {
        this.id = id;
        this.productName = productName;
        this.productId = productId;
        this.productText = productText;
    }

    public UUID getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public UUID getProductId() {
        return productId;
    }

    public String getProductText() {
        return productText;
    }

    public List<RuleCondition> getConditions() {
        return conditions;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public void setProductText(String productText) {
        this.productText = productText;
    }

    public void setConditions(List<RuleCondition> conditions) {
        this.conditions = conditions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DynamicRule that = (DynamicRule) o;
        return Objects.equals(id, that.id) && Objects.equals(productName, that.productName) && Objects.equals(productId, that.productId) && Objects.equals(productText, that.productText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, productId, productText);
    }

    public void setRule(List<RuleCondition> condition) {
    }
}
