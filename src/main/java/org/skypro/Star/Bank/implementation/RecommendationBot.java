package org.skypro.Star.Bank.implementation;

import org.skypro.Star.Bank.model.DTO;
import org.skypro.Star.Bank.model.User;
import org.skypro.Star.Bank.service.RecommendationService;
import org.skypro.Star.Bank.service.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class RecommendationBot extends TelegramLongPollingBot {

    private final String botUsername;
    private final UserService userService;
    private final RecommendationService recommendationService;

    public RecommendationBot(
            String botToken,
            UserService userService,
            RecommendationService recommendationService
    ) {
        super();
        this.botUsername = "BankStarRecommendationBot";
        this.userService = userService;
        this.recommendationService = recommendationService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) return;

        Message message = update.getMessage();
        String text = message.getText();
        Long chatId = message.getChatId();

        if (text.equals("/start")) {
            sendHelp(chatId);
        } else if (text.startsWith("/recommend")) {
            processRecommendCommand(chatId, text);
        } else {
            sendMessage(chatId, "Неизвестная команда. Используйте /recommend <имя>");
        }
    }

    private void processRecommendCommand(Long chatId, String text) {
        String[] parts = text.split(" ", 2);
        if (parts.length < 2) {
            sendMessage(chatId, "Пожалуйста, укажите имя пользователя: /recommend <имя>");
            return;
        }

        String username = parts[1].trim();
        List<User> users = userService.findUsersByName(username);

        if (users.isEmpty()) {
            sendMessage(chatId, "Пользователь не найден");
        } else if (users.size() > 1) {
            sendMessage(chatId, "Найдено несколько пользователей. Уточните запрос");
        } else {
            User user = users.get(0);
            List<DTO> recommendations =
                    recommendationService.getRecommendations(user.getId().toString());

            sendRecommendations(chatId, user, recommendations);
        }
    }

    private void sendRecommendations(Long chatId, User user, List<DTO> recommendations) {
        StringBuilder response = new StringBuilder();
        response.append("Здравствуйте, ").append(user.getFullName()).append("!\n\n");

        if (recommendations.isEmpty()) {
            response.append("На данный момент для вас нет рекомендаций.");
        } else {
            response.append("Новые продукты для вас:\n\n");
            for (DTO rec : recommendations) {
                response.append("• ").append(rec.name()).append("\n");
                response.append(rec.text()).append("\n\n");
            }
        }

        sendMessage(chatId, response.toString());
    }

    private void sendHelp(Long chatId) {
        String helpText = "Добро пожаловать в банк «Стар»! 🏦\n\n"
                + "Для получения рекомендаций используйте команду:\n"
                + "/recommend <Имя Фамилия>\n\n"
                + "Например: /recommend Иван Иванов";
        sendMessage(chatId, helpText);
    }

    private void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {

        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }
}