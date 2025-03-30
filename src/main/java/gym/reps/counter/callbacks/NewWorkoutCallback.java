package gym.reps.counter.callbacks;

import gym.reps.counter.database.repository.MuscleGroupRepository;
import gym.reps.counter.model.entity.MuscleGroup;
import gym.reps.counter.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static gym.reps.counter.utils.BotUpdateUtil.getCallbackChatIdAsString;

@Component
@RequiredArgsConstructor
public class NewWorkoutCallback implements CallbackHandler {

    private final MuscleGroupRepository muscleGroupRepository;

    @Override
    public SendMessage apply(Callback callback, Update update) {
        String chatId = getCallbackChatIdAsString(update.getCallbackQuery());
        SendMessage answer = new SendMessage(chatId, "Choose muscle group");
        addTypesKeyboard(answer);
        return answer;
    }

    private void addTypesKeyboard(SendMessage answer) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = muscleGroupRepository.findAll().stream()
                .map(muscleGroup -> List.of(createMuscleGroupButton(muscleGroup)))
                .toList();
        inlineKeyboardMarkup.setKeyboard(rowList);
        answer.setReplyMarkup(inlineKeyboardMarkup);
    }

    private InlineKeyboardButton createMuscleGroupButton(MuscleGroup muscleGroup) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(muscleGroup.getName());
        String jsonCallback = JsonUtil.toJson(List.of(CallbackType.NEW_EXERCISE, muscleGroup.getId()));
        inlineKeyboardButton.setCallbackData(jsonCallback);
        return inlineKeyboardButton;
    }
}
