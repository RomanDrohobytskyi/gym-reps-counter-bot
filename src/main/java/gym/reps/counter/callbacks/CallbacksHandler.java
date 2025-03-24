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

@Slf4j
@Component
public class CallbacksHandler {

    private final Map<CallbackType, CallbackHandler> callbacks;
    private final Map<CallbackType, CallbackHandler> callbacksWithNestedInpt;

    public CallbacksHandler(NewWorkoutCallback newWorkoutCallback,
                            NewExerciseCallback newExerciseCallback,
                            RegisterExerciseCallback registerExerciseCallback) {
        this.callbacks = Map.of(CallbackType.NEW_WORKOUT, newWorkoutCallback,
                CallbackType.NEW_EXERCISE, newExerciseCallback,
                CallbackType.CHOSEN_EXERCISE, registerExerciseCallback);
        this.callbacksWithNestedInpt = Map.of(CallbackType.CHOSEN_EXERCISE, registerExerciseCallback);
    }

    public SendMessage handleCallbacks(AbsSender bot, Update update) {
        if (update.hasCallbackQuery()) {

            List<String> callbackQueryData = JsonUtil.toList(update.getCallbackQuery().getData());
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            if (CollectionUtils.isEmpty(callbackQueryData)) {
                return new SendMessage(String.valueOf(chatId), "Callback is empty");
            } else {
                Callback callback = Callback.builder()
                        .callbackType(CallbackType.valueOf(callbackQueryData.get(0)))
                        .data(callbackQueryData.get(1))
                        .build();
                CallbackHandler callbackHandler = callbacks.get(callback.getCallbackType());
                return callbackHandler.apply(bot, callback, update);
            }
        }

        return callbacksWithNestedInpt.get(CallbackType.CHOSEN_EXERCISE).apply(bot, null, update);
    }
}