package org.skypro.Star.Bank;

import org.junit.jupiter.api.Test;
import org.skypro.Star.Bank.controller.RuleController;
import org.skypro.Star.Bank.dynamic.DynamicRule;
import org.skypro.Star.Bank.dynamic.RuleCondition;
import org.skypro.Star.Bank.model.RuleStat;
import org.skypro.Star.Bank.repository.DynamicRuleRepository;
import org.skypro.Star.Bank.service.DynamicRuleService;
import org.skypro.Star.Bank.service.RuleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static java.lang.reflect.Array.get;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RuleController.class)
@Import({DynamicRuleService.class, RuleValidator.class})
@AutoConfigureMockMvc(addFilters = false)
public class RuleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DynamicRuleRepository dynamicRuleRepository;

    @Test
    void createRule_ShouldReturnCreatedStatus() throws Exception {
        DynamicRule rule = createTestRule();
        when(dynamicRuleRepository.save(any())).thenReturn(rule);

        mockMvc.perform(get("/rule").contentType(MediaType.APPLICATION_JSON).content("""
{
 "product_name" : "Test Product",
 "product_id": "147f6a0f-3b91-413b-ab99-87f081d60d5a",
 "product_text": "Test description",
 "rule": [
 {
 "query": "USER_OF",
 "arguments": ["DEBIT"],
 "negate": false
 }
 ]
 }
 """)).andExpect(status().isCreated()).andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.product_name").value("Test Product"));
    }

    @Test
    void getAllRules_ShouldReturnOkStatus() throws Exception {
        when(dynamicRuleRepository.findAll()).thenReturn(List.of(createTestRule()));

        mockMvc.perform(get("/rule")).andExpect(status().isOk()).andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(1));
    }

    @Test
    void deleteRule_ShouldReturnNoContentStatus() throws Exception {
        UUID ruleId = UUID.randomUUID();
        doNothing().when(dynamicRuleRepository).deleteById(ruleId);

        mockMvc.perform(delete("/rule/{id}", ruleId)).andExpect(status().isNoContent());
    }

    @Test
    void getRuleStats_ShouldReturnStatistics() throws Exception {
        when(dynamicRuleRepository.findAll()).thenReturn(List.of(createTestStatistic()));

        mockMvc.perform(get("/rule/stats")).andExpect(status().isOk()).andExpect(jsonPath("$.stats").isArray());
    }
    private DynamicRule createTestRule() {
        DynamicRule rule = new DynamicRule();
        rule.setId(UUID.randomUUID());
        rule.setProductName("Test Product");
        rule.setProductId(UUID.randomUUID());
        rule.setProductText("Test description");

        RuleCondition condition = new RuleCondition();
        condition.setQuery("USER_OF");
        condition.setArguments(List.of("DEBIT"));
        condition.setNegate(false);

        rule.setRule(List.of(condition));
        return rule;
    }
    private RuleStat createTestStatistic() {
        RuleStat statistic = new RuleStat();
        statistic.setId(UUID.randomUUID());
        statistic.setRule(createTestRule());
        statistic.setCount(5L);
        return statistic;
    }
}
