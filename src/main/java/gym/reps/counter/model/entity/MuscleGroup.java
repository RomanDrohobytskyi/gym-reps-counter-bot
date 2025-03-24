package gym.reps.counter.model.entity;

import gym.reps.counter.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "muscle_groups")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MuscleGroup extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String name;
    @OneToMany(mappedBy = "muscleGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Exercise> exercises;
}
