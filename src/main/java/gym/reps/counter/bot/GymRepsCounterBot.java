package gym.reps.counter.bot;

import gym.reps.counter.callbacks.CallbacksHandler;
import gym.reps.counter.commands.CommandsHandler;
import gym.reps.counter.secrets.reader.SecretsReader;
import gym.reps.counter.utils.BotUpdateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class GymRepsCounterBot extends TelegramLongPollingBot {

    private final CommandsHandler commandsHandler;
    private final CallbacksHandler callbacksHandler;
    private final SecretsReader botSecretsReader = SecretsReader.getBotConfigReader();

    @Override
    public String getBotUsername() {
        return botSecretsReader.readSecret(SecretsReader.BOT_NAME);
    }

    @Override
    public String getBotToken() {
        return botSecretsReader.readSecret(SecretsReader.BOT_TOKEN);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (BotUpdateUtil.isCommand(update)) {
                sendMessage(commandsHandler.handleCommands(update));
        } else if (BotUpdateUtil.hasMessage(update) || update.hasCallbackQuery()) {
            sendMessage(callbacksHandler.handleCallbacks(this, update));
        } else {
            sendMessage(new SendMessage(BotUpdateUtil.getChatId(update), "This command is not supported"));
        }
    }

    private void sendMessage(SendMessage sendMessage) {
        try {
            if (Objects.nonNull(sendMessage)) {
                execute(sendMessage);
            } else {
                log.info("No message received");
            }
        } catch (TelegramApiException e) {
            log.error("Telegram Api Exception occurred: ", e);
        }
    }

}
