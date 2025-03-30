package gym.reps.counter.model.entity;

import gym.reps.counter.common.BaseEntity;
import gym.reps.counter.model.dto.UserWorkoutData;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "workout_exercises")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WorkoutExercise extends BaseEntity {

    private int sets;
    private int reps;
    private int weight;
//    private int restTime; //TODO
    private Date startDate;

    @ManyToOne
    @JoinColumn(name = "workout_id", nullable = false)
    private Workout workout;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    public static WorkoutExercise fromWorkout(UserWorkoutData data, Workout currentWorkout) {
        return WorkoutExercise.builder()
                .startDate(data.getStartTime())
                .exercise(data.getExercise())
                .sets(Integer.parseInt(data.getSets()))
                .weight(Integer.parseInt(data.getWeight()))
                .reps(Integer.parseInt(data.getReps()))
                .workout(currentWorkout)
                .build();
    }

}
