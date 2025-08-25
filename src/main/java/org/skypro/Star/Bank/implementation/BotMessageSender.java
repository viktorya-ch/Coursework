package org.skypro.Star.Bank.implementation;


import org.skypro.Star.Bank.model.DTO;
import org.skypro.Star.Bank.model.User;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

/**
 * Сервис для форматирования и отправки сообщений в Telegram
 */
@Component
public class BotMessageSender {
    private final TelegramLongPollingBot bot;

    public BotMessageSender(TelegramLongPollingBot bot) {
        this.bot = bot;
    }

    /**
     * Отправляет отформатированный список рекомендаций
     * @param chatId Идентификатор чата в Telegram
     * @param user Данные пользователя
     * @param recommendations Список рекомендаций
     */
    public void sendRecommendations(Long chatId, User user, List<DTO> recommendations) {
        StringBuilder response = new StringBuilder();
        response.append(" Привет! ").append(user.getFullName()).append("!\n\n");
        if (recommendations.isEmpty()) {
            response.append(" На данный момент для вас нет рекомендаций ");
        } else {
            response.append(" Новые продукты для вас:\n\n");
            for (DTO rec : recommendations) {
                response.append(" ").append(rec.name()).append("\n");
                response.append(rec.text()).append("\n\n");
            }
        }
        sendMessage(chatId, response.toString());
    }

    /**
     * Отправляет текстовые сообщения в Telegram
     * @param chatId Идентификатор чата в Telegram
     * @param text Текст сообщения
     */
    void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);

        message.setParseMode(ParseMode.MARKDOWN);
        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
        }
    }

    void sendHelp(Long chatId) {
        String helpText = " Добро пожаловать в банк Стар! \n\n" + " Для получения рекомендаций используйте команду : \n " + " /recommend <Имя Фамилия>\n\n " + " Например: /recommed <Александр Белов>";
        sendMessage(chatId, helpText);
    }
}
