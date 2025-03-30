package gym.reps.counter.callbacks;

import gym.reps.counter.database.repository.ExerciseRepository;
import gym.reps.counter.database.repository.MuscleGroupRepository;
import gym.reps.counter.model.entity.Exercise;
import gym.reps.counter.model.entity.MuscleGroup;
import gym.reps.counter.utils.BotUpdateUtil;
import gym.reps.counter.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewExerciseCallback implements CallbackHandler {

    private final static String CHOOSE_EXERCISE_MESSAGE = "Choose exercise for %s";
    private final MuscleGroupRepository muscleGroupRepository;
    private final ExerciseRepository exerciseRepository;

    @Override
    public SendMessage apply(Callback callback, Update update) {
        Long muscleGroupId = Long.valueOf(callback.getData());
        MuscleGroup muscleGroup = muscleGroupRepository.findById(muscleGroupId)
                .orElseThrow(() -> new IllegalArgumentException("No Muscle Group found for id: " + muscleGroupId));
        SendMessage answer = new SendMessage(BotUpdateUtil.getChatId(update), String.format(CHOOSE_EXERCISE_MESSAGE, muscleGroup.getName()));
        addTypesKeyboard(answer, muscleGroup);
        return answer;
    }

    private void addTypesKeyboard(SendMessage answer, MuscleGroup muscleGroup) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowList = exerciseRepository.findByMuscleGroup(muscleGroup).stream()
                .map(exercise -> List.of(createExerciseGroupButton(exercise)))
                .toList();
        inlineKeyboardMarkup.setKeyboard(rowList);
        answer.setReplyMarkup(inlineKeyboardMarkup);
    }

    private InlineKeyboardButton createExerciseGroupButton(Exercise exercise) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(exercise.getName());
        String jsonCallback = JsonUtil.toJson(List.of(CallbackType.CHOSEN_EXERCISE, exercise.getId()));
        inlineKeyboardButton.setCallbackData(jsonCallback);
        return inlineKeyboardButton;
    }
}
