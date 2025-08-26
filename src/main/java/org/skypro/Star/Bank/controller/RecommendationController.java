package org.skypro.Star.Bank.controller;

import org.skypro.Star.Bank.model.DTO;
import org.skypro.Star.Bank.model.RecommendationResponse;
import org.skypro.Star.Bank.service.RecommendationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecommendationController {
    private final RecommendationService service;

    public RecommendationController(RecommendationService service) {
        this.service = service;
    }

    @GetMapping("/recommendation/{userId}")
    public RecommendationResponse getRecommendations(@PathVariable String userId) {
        List<DTO> recommendations = service.getRecommendations(userId);
        return new RecommendationResponse(userId, recommendations);
    }

}
