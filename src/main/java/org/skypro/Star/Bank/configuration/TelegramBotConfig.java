package org.skypro.Star.Bank.configuration;

import org.skypro.Star.Bank.implementation.RecommendationBot;
import org.skypro.Star.Bank.service.RecommendationService;
import org.skypro.Star.Bank.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class TelegramBotConfig {

    @Value("${telegram.bot.token}")
    private String botToken;

    @Bean
    public TelegramBotsApi telegramBotsApi(RecommendationBot recommendationBot)
            throws TelegramApiException {

        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot((LongPollingBot) recommendationBot);
        return botsApi;
    }

    @Bean
    public RecommendationBot recommendationBot(UserService userService,
            RecommendationService recommendationService) {
        return new RecommendationBot(botToken, userService, recommendationService);
    }
}