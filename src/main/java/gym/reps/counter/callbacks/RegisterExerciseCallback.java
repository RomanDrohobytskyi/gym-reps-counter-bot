package gym.reps.counter.callbacks;

import gym.reps.counter.database.repository.ExerciseRepository;
import gym.reps.counter.database.repository.WorkoutExerciseRepository;
import gym.reps.counter.database.repository.WorkoutRepository;
import gym.reps.counter.model.dto.UserWorkoutData;
import gym.reps.counter.model.entity.Exercise;
import gym.reps.counter.model.entity.Workout;
import gym.reps.counter.model.entity.WorkoutExercise;
import gym.reps.counter.utils.BotUpdateUtil;
import gym.reps.counter.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RegisterExerciseCallback implements CallbackHandler {

    private enum State {WAITING_FOR_WEIGHT, WAITING_FOR_REPS, WAITING_FOR_SETS, NONE}

    private final static String SAVED_EXERCISE_MESSAGE = "Workout logged:\n🏋️ %s\n⚖️ %s kg\n🔄 %s reps\n📦 %s sets";
    private final ExerciseRepository exerciseRepository;
    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final WorkoutRepository workoutRepository;


    private final Map<Long, UserWorkoutData> userWorkoutData = new HashMap<>();
    private final Map<Long, State> userProgress = new HashMap<>();

    private Workout currentWorkout;

    @Override
    public SendMessage apply(AbsSender bot, Callback callback, Update update) {
        String chatId = BotUpdateUtil.getChatId(update);
        if (update.hasCallbackQuery()) {
            return handleCallback(bot, callback.getData(), update.getCallbackQuery());
        } else if (update.hasMessage() && update.getMessage().hasText()) {
            return handleUserInput(bot, update.getMessage());
        }

        throw new IllegalArgumentException("Not supported operation!");


/*
        WorkoutExercise workoutExercise = WorkoutExercise.builder()
                .exercise(exercise)
                .workout(exercise.getWorkout())
                .startDate(LocalDateTime.now())
                .build();
*/

//        handleCallback(bot, update.getCallbackQuery());

/*        SendMessage answer = new SendMessage(String.valueOf(chatId), exercise.getName());
        //addTypesKeyboard(answer, exercise);
        return answer;*/


//        return new SendMessage(chatId, "....");
    }

    // Handle callback when a user selects an exercise
    public SendMessage handleCallback(AbsSender bot, String exId, CallbackQuery callbackQuery) {
        long chatId = callbackQuery.getMessage().getChatId();
        Long exerciseId = Long.valueOf(exId);
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new IllegalArgumentException("Exercise not found"));


        // Save selected exercise and start asking for weight
        userWorkoutData.put(chatId, new UserWorkoutData(exercise));
        userProgress.put(chatId, State.WAITING_FOR_WEIGHT);
        if (this.currentWorkout == null) {
            this.currentWorkout = new Workout();
            this.currentWorkout.setName("Workout");
            this.currentWorkout.setDescription("Workout description");
        }
        if (this.currentWorkout.getId() == null) {
            this.currentWorkout = workoutRepository.save(this.currentWorkout);
        }

//        exercise.setWorkout(this.currentWorkout);

//        SendMessageUtil.sendMessage(bot, chatId, "You selected " + exercise.getName() + ". Enter weight (kg):");
        return new SendMessage(String.valueOf(chatId), "You selected " + exercise.getName() + ". Enter weight (kg):");

    }

    // Handle user input (weight, reps, sets)
    public SendMessage handleUserInput(AbsSender bot, Message message) {
        long chatId = message.getChatId();
        String chatIdStringify = String.valueOf(message.getChatId());
        String text = message.getText();

        if (!userWorkoutData.containsKey(chatId)) {
//            SendMessageUtil.sendMessage(bot, chatId, "Please select an exercise first.");
//            return;
            return new SendMessage(chatIdStringify, "Please select an exercise first.");
        }

        UserWorkoutData data = userWorkoutData.get(chatId);
        State currentState = userProgress.get(chatId);

        switch (currentState) {
            case WAITING_FOR_WEIGHT:
                data.setWeight(text);
                userProgress.put(chatId, State.WAITING_FOR_REPS);
//                SendMessageUtil.sendMessage(bot, chatId, "Enter reps:");
                return new SendMessage(chatIdStringify, "Enter reps:");

                //break;

            case WAITING_FOR_REPS:
                data.setReps(text);
                userProgress.put(chatId, State.WAITING_FOR_SETS);
//                SendMessageUtil.sendMessage(bot, chatId, "Enter sets:");
                return new SendMessage(chatIdStringify, "Enter sets:");
               // break;

            case WAITING_FOR_SETS:
                data.setSets(text);
                userProgress.put(chatId, State.NONE);  // Reset state after logging

                // Confirm workout log
                /*SendMessageUtil.sendMessage(bot, chatId, String.format(
                        "Workout logged:\n🏋️ %s\n⚖️ %s kg\n🔄 %s reps\n📦 %s sets",
                        data.getExercise(), data.getWeight(), data.getReps(), data.getSets()
                ));*/



                WorkoutExercise workoutExercise = WorkoutExercise.builder()
                        .startDate(data.getStartTime())
                        .exercise(data.getExercise())
                        .sets(Integer.parseInt(data.getSets()))
                        .weight(Integer.parseInt(data.getWeight()))
                        .reps(Integer.parseInt(data.getReps()))
                        .workout(this.currentWorkout)
                        .build();

                this.currentWorkout.addExercise(workoutExercise);
                this.workoutRepository.save(this.currentWorkout);
                //save to db
//                workoutExerciseRepository.save(workoutExercise);


                String formatted = String.format(SAVED_EXERCISE_MESSAGE, currentWorkout.getName(),
                        workoutExercise.getWeight(), workoutExercise.getReps(), workoutExercise.getSets());


                // Remove user data
                userWorkoutData.remove(chatId);
                this.currentWorkout = null;
                return new SendMessage(chatIdStringify, formatted);

                //break;

            default:
//                SendMessageUtil.sendMessage(bot, chatId, "Please select an exercise first.");
                return new SendMessage(chatIdStringify, "Please select an exercise first.");

           // break;
        }
    }

        /*public InlineKeyboardMarkup getExerciseKeyboard() {
            InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rows = new ArrayList<>();

            List<InlineKeyboardButton> row1 = List.of(
                    new InlineKeyboardButton("🏋️ Bench Press").setCallbackData("Bench Press"),
                    new InlineKeyboardButton("🏋️ Squat").setCallbackData("Squat")
            );
            rows.add(row1);

            markup.setKeyboard(rows);
            return markup;
        }*/



// todo - chest -> dumbbell bench press -> counts -> kg -> register


    //todo rename
    private void addTypesKeyboard(SendMessage answer, Exercise exercise) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        //TODO
        // 1 button show 3 last, 2 button show history?

        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText("Register");
        String jsonCallback = JsonUtil.toJson(List.of(CallbackType.CHOSEN_EXERCISE, exercise.getId()));
        inlineKeyboardButton.setCallbackData(jsonCallback);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();/*exerciseRepository.findByMuscleGroup(muscleGroup).stream()
                .map(exercise -> List.of(createExerciseGroupButton(exercise)))
                .toList();*/
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