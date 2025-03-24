package gym.reps.counter.utils;

import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.objects.Update;

=import static org.apache.commons.lang3.StringUtils.startsWith;

@UtilityClass
public class BotUpdateUtil {

    public static final String COMMAND_PREFIX = "/";

    public static boolean isCommand(Update update) {
        return hasMessage(update) && startsWith(update.getMessage().getText(), COMMAND_PREFIX);
    }

    public static boolean hasMessage(Update update) {
        return update.hasMessage() && update.getMessage().hasText();
    }

    public static String getChatId(Update update) {
        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getMessage().getChatId().toString();
        }
        else if (update.hasMessage()) {
            return update.getMessage().getChatId().toString();
        }
        throw new IllegalArgumentException("No chat id found");
    }
}
