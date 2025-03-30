package gym.reps.counter.callbacks;

import gym.reps.counter.database.repository.ExerciseRepository;
import gym.reps.counter.database.repository.WorkoutRepository;
import gym.reps.counter.model.WorkoutState;
import gym.reps.counter.model.dto.UserWorkoutData;
import gym.reps.counter.model.entity.Exercise;
import gym.reps.counter.model.entity.Workout;
import gym.reps.counter.model.entity.WorkoutExercise;
import gym.reps.counter.service.UserWorkoutService;
import gym.reps.counter.utils.BotUpdateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static gym.reps.counter.utils.BotUpdateUtil.getCallbackChatId;

@Component
@RequiredArgsConstructor
public class RegisterExerciseCallback implements CallbackHandler {

    private final static String SAVED_EXERCISE_MESSAGE = "Workout logged:\n🏋️ %s\n⚖️ %s kg\n🔄 %s reps\n📦 %s sets";
    private final UserWorkoutService userWorkoutService = new UserWorkoutService();
    private final ExerciseRepository exerciseRepository;
    private final WorkoutRepository workoutRepository;
    private Workout currentWorkout;

    @Override
    public SendMessage apply(Callback callback, Update update) {
        if (update.hasCallbackQuery()) {
            return handleCallback(callback.getData(), update.getCallbackQuery());
        } else if (BotUpdateUtil.hasMessage(update)) {
            return handleUserInput(update.getMessage());
        }

        throw new IllegalArgumentException("Not supported operation!");
    }

    private SendMessage handleCallback(String exerciseId, CallbackQuery callbackQuery) {
        Long chatId = getCallbackChatId(callbackQuery);
        Exercise exercise = getExercise(exerciseId);

        userWorkoutService.startWorkout(chatId, exercise);
        startWorkout();

        return new SendMessage(chatId.toString(), "You selected " + exercise.getName() + ". Enter weight (kg):");
    }

    private void startWorkout() {
        if (this.currentWorkout == null) {
            this.currentWorkout = new Workout();
            this.currentWorkout.setName("Workout");
            this.currentWorkout.setDescription("Workout description");
            this.currentWorkout = workoutRepository.save(this.currentWorkout);
        }
    }

    private Exercise getExercise(String exId) {
        Long exerciseId = Long.valueOf(exId);
        return exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new IllegalArgumentException("Exercise not found"));
    }

    private SendMessage handleUserInput(Message message) {
        Long chatId = message.getChatId();

        if (!userWorkoutService.exerciseWasSelected(chatId)) {
            return new SendMessage(chatId.toString(), "Please select an exercise first.");
        }

        UserWorkoutData data = userWorkoutService.getWorkoutData(chatId);
        WorkoutState currentState = userWorkoutService.getWorkoutState(chatId);

        return switch (currentState) {
            case WAITING_FOR_WEIGHT -> handleWeight(data, message.getText());
            case WAITING_FOR_REPS -> handleReps(data, message.getText());
            case WAITING_FOR_SETS -> handleSetsAndSaveExercise(data, message.getText());
            default -> new SendMessage(chatId.toString(), "Please select an exercise first.");
        };
    }

    private SendMessage handleWeight(UserWorkoutData data, String userInput) {
        userWorkoutService.addWeight(data.getChatId(), userInput);
        return new SendMessage(data.getStringChatId(), "Enter reps:");
    }

    private SendMessage handleReps(UserWorkoutData data, String userInput) {
        userWorkoutService.addReps(data.getChatId(), userInput);
        return new SendMessage(data.getStringChatId(), "Enter sets:");
    }

    private SendMessage handleSetsAndSaveExercise(UserWorkoutData data, String userInput) {
        data.setSets(userInput);

        WorkoutExercise workoutExercise = WorkoutExercise.fromWorkout(data, this.currentWorkout);
        this.currentWorkout.addExercise(workoutExercise);
        this.workoutRepository.save(this.currentWorkout);

        String formatted = SAVED_EXERCISE_MESSAGE.formatted(currentWorkout.getName(),
                workoutExercise.getWeight(), workoutExercise.getReps(), workoutExercise.getSets());

        resetWorkout(data);
        return new SendMessage(data.getStringChatId(), formatted);
    }

    private void resetWorkout(UserWorkoutData data) {
        userWorkoutService.resetWorkout(data.getChatId());
        this.currentWorkout = null;
    }
}