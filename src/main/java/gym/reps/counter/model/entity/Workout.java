package gym.reps.counter.model.entity;

import gym.reps.counter.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "workouts")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Workout extends BaseEntity {

    private String name;
    private String description;

    @Column(name = "creation_date")
    @CreationTimestamp
    private Date creationDate;
    @Column(name = "update_date")
    @CreationTimestamp
    private Date updateDate;

    @Builder.Default
    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL)
    private List<WorkoutExercise> exercises = new ArrayList<>();

    public Workout addExercise(WorkoutExercise exercise) {
        getExercises().add(exercise);
        return this;
    }

}
