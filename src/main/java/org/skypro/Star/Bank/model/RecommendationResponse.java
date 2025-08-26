package org.skypro.Star.Bank.model;

import java.util.List;

public record RecommendationResponse(String user_id, List<DTO>recommendation) {
}
