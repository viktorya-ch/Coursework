package org.skypro.Star.Bank;

import org.junit.jupiter.api.Test;
import org.skypro.Star.Bank.controller.RecommendationController;
import org.skypro.Star.Bank.model.DTO;
import org.skypro.Star.Bank.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static java.lang.reflect.Array.get;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecommendationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RecommendationService recommendationService;

    @Test
    void getRecommendations_ShouldReturnRecommendations() throws Exception {
        DTO recommendation = new DTO(
                UUID.randomUUID().toString(),
                "Test Product",
                "Test description"
        );
        when(recommendationService.getRecommendations(anyString())).thenReturn(List.of(recommendation));
        mockMvc.perform(get("/recommendation/{userId}", "test-user-id")).andExpect(status().isOk())
                .andExpect(jsonPath("$.user_id").value("test-user-id")).andExpect(jsonPath("$.recommendations").isArray())
                .andExpect(jsonPath("$.recommendations.length()").value(1));
    }

    @Test
    void getRecommendations_ShouldReturnEmptyList() throws Exception {
        when(recommendationService.getRecommendations(anyString())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/recommendation/{userId}", "test-user-id")).andExpect(status().isOk())
                .andExpect(jsonPath("$.recommendations").isArray()).andExpect(jsonPath("/recommendations").isEmpty());

    }
}

