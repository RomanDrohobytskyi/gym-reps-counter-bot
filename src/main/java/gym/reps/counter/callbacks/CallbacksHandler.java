package gym.reps.counter.callbacks;

import gym.reps.counter.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.List;
import java.util.Map;

import static gym.reps.counter.utils.BotUpdateUtil.getCallbackChatId;

@Slf4j
@Component
public class CallbacksHandler {

    private final Map<CallbackType, CallbackHandler> callbacks;
    private final Map<CallbackType, CallbackHandler> callbacksWithNestedInput;

    public CallbacksHandler(NewWorkoutCallback newWorkoutCallback,
                            NewExerciseCallback newExerciseCallback,
                            RegisterExerciseCallback registerExerciseCallback) {
        this.callbacks = Map.of(CallbackType.NEW_WORKOUT, newWorkoutCallback,
                CallbackType.NEW_EXERCISE, newExerciseCallback,
                CallbackType.CHOSEN_EXERCISE, registerExerciseCallback);
        this.callbacksWithNestedInput = Map.of(CallbackType.CHOSEN_EXERCISE, registerExerciseCallback);
    }

    public SendMessage handleCallbacks(AbsSender bot, Update update) {
        if (update.hasCallbackQuery()) {
            List<String> callbackQueryData = JsonUtil.toList(update.getCallbackQuery().getData());

            if (CollectionUtils.isEmpty(callbackQueryData)) {
                long chatId = getCallbackChatId(update.getCallbackQuery());
                return new SendMessage(String.valueOf(chatId), "Callback is empty");
            } else {
                Callback callback = Callback.of(callbackQueryData);
                CallbackHandler callbackHandler = callbacks.get(callback.getCallbackType());
                return callbackHandler.apply(callback, update);
            }
        }

        return callbacksWithNestedInput.get(CallbackType.CHOSEN_EXERCISE).apply(null, update);
    }
}