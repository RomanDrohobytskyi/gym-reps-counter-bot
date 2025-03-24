package gym.reps.counter.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@UtilityClass
public class SendMessageUtil {

    public static void sendMessage(AbsSender bot, long chatId, String text) {
        SendMessage message = new SendMessage(String.valueOf(chatId), text);
        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            log.error("Telegram Api Exception occurred: ", e);
        }
    }

}
