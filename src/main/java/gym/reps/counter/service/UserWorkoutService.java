package gym.reps.counter.service;

import gym.reps.counter.model.WorkoutState;
import gym.reps.counter.model.dto.UserWorkoutData;
import gym.reps.counter.model.entity.Exercise;

import java.util.HashMap;
import java.util.Map;

import static gym.reps.counter.model.WorkoutState.*;

public class UserWorkoutService {

    private final Map<Long, UserWorkoutData> userWorkoutData = new HashMap<>();
    private final Map<Long, WorkoutState> userProgress = new HashMap<>();

    public void startWorkout(Long chatId, Exercise exercise) {
        userWorkoutData.put(chatId, new UserWorkoutData(exercise, chatId));
        userProgress.put(chatId, WAITING_FOR_WEIGHT);
    }

    public void addWeight(Long chatId, String weightInKg) {
        getWorkoutData(chatId).setWeight(weightInKg);
        userProgress.put(chatId, WAITING_FOR_REPS);
    }

    public void addReps(Long chatId, String weightInKg) {
        getWorkoutData(chatId).setReps(weightInKg);
        userProgress.put(chatId, WAITING_FOR_SETS);
    }

    public UserWorkoutData getWorkoutData(Long chatId) {
        return userWorkoutData.get(chatId);
    }

    public WorkoutState getWorkoutState(Long chatId) {
        return userProgress.getOrDefault(chatId, NONE);
    }

    public void resetWorkout(Long chatId) {
        userWorkoutData.remove(chatId);
        userProgress.put(chatId, NONE);;
    }

    public boolean exerciseWasSelected(Long chatId) {
        return userWorkoutData.containsKey(chatId);
    }
}
