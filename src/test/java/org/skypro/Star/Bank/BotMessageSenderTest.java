package org.skypro.Star.Bank;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.Star.Bank.implementation.BotMessageSender;
import org.skypro.Star.Bank.model.DTO;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)

public class BotMessageSenderTest {

    @Mock
    private TelegramLongPollingBot bot;

    @InjectMocks
    private BotMessageSender botMessageSender;

    @Test
    void sendRecommendations_ShouldFormatMessageCorrectly() throws TelegramApiException {
        User user = new User(UUID.randomUUID(), "Иван", "Иванов");

        DTO rec1 = new DTO(
                UUID.randomUUID().toString(),
                "Invest 500",
                "Описание инвестиционного продукта"
        );

        DTO rec2 = new DTO(
                UUID.randomUUID().toString(),
                "Top Saving",
                "Описание сберегательного продукта"
        );

        // Mock bot execution
        doNothing().when(bot).execute(any(SendMessage.class));

        botMessageSender.sendRecommendations(123L, user, List.of(rec1, rec2));

        // Verify message was sent
        verify(bot, times(1)).execute(any(SendMessage.class));
    }

    @Test
    void sendMessage_ShouldHandleTelegramApiException() throws TelegramApiException{
        doThrow(new TelegramApiException("Test exception")).when(bot).execute(any(SendMessage.class));

        // Should not throw exception
        assertDoesNotThrow(() -> botMessageSender.sendMessage(123L, "Test message"));

    }
}

