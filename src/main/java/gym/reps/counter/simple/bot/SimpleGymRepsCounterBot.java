package gym.reps.counter.simple.bot;

import gym.reps.counter.secrets.reader.SecretsReader;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class SimpleGymRepsCounterBot extends TelegramLongPollingBot {

    private final SecretsReader botSecretsReader = SecretsReader.getBotConfigReader();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            SendMessage message = SendMessage.builder()
                    .chatId(chatId)
                    .text(messageText)
                    .build();
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public String getBotUsername() {
        return botSecretsReader.readSecret(SecretsReader.BOT_NAME);
    }

    @Override
    public String getBotToken() {
        return botSecretsReader.readSecret(SecretsReader.BOT_TOKEN);
    }
}
