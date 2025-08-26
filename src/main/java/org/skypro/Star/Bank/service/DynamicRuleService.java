package org.skypro.Star.Bank.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.skypro.Star.Bank.dynamic.DynamicRule;
import org.skypro.Star.Bank.model.DynamicRuleRequest;
import org.skypro.Star.Bank.model.DynamicRuleResponse;
import org.skypro.Star.Bank.model.RuleListResponse;
import org.skypro.Star.Bank.repository.DynamicRuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для работы с динамическими правилами рекомендаций
 */
@Service
public class DynamicRuleService {
    private final DynamicRuleRepository repository;
    private final ObjectMapper objectMapper;

    public DynamicRuleService(DynamicRuleRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    /**
     * Создает новое динамическое правило
     *
     * @param request запрос с данными правила
     * @return созданное правило
     * @throws IllegalArgumentException если правило не прошло валидацию
     */
    @Transactional
    public DynamicRuleResponse createRule(DynamicRuleRequest request) {
        DynamicRule entity = new DynamicRule();

        entity.setProductName(request.productName());
        entity.setProductId(request.productId());
        entity.setProductText(request.productText());
        entity.setConditions(request.rule());
        DynamicRule saved = repository.save(entity);
        return mapToResponse(saved);
    }

    /**
     * Получает все активные правила
     * @return Обертка со списком правил
     */
    public RuleListResponse getAllRules() {
        List<DynamicRuleResponse> rules = repository.findAll().stream().map(this::mapToResponse).toList();
        return new RuleListResponse(rules);
    }

    @Transactional
    public void deleteRule(UUID id) {
        repository.deleteById(id);
    }

    private DynamicRuleResponse mapToResponse(DynamicRule entity) {
        return new DynamicRuleResponse()
        entity.getId();
        entity.getProductName();
        entity.getProductId();
        entity.getProductText();
        entity.getConditions();
    }
}