package gym.reps.counter.model.entity;

import gym.reps.counter.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "workout_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutLog extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "workout_id", nullable = false)
    private Workout workout;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    private LocalDate date = LocalDate.now();
    private int completedSets;
    private int completedReps;
    private double weightUsed; // kg/lbs
    private int duration; // in seconds
}