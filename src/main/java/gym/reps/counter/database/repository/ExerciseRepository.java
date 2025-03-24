package gym.reps.counter.database.repository;

import gym.reps.counter.model.entity.Exercise;
import gym.reps.counter.model.entity.MuscleGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ExerciseRepository extends CrudRepository<Exercise, Long> {
    Set<Exercise> findByMuscleGroup(MuscleGroup muscleGroup);
}
