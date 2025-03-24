package gym.reps.counter.callbacks;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

public interface CallbackHandler {

    default SendMessage apply(Callback callback, Update update) {
        return null;
    }

    default SendMessage apply(AbsSender bot, Callback callback, Update update) {
        return this.apply(callback, update);
    }
}
