package gym.reps.counter.model.entity;

import gym.reps.counter.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "exercises")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Exercise extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String name;
    private String description;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "muscleGroup_id", nullable = false)
    private MuscleGroup muscleGroup;
}
