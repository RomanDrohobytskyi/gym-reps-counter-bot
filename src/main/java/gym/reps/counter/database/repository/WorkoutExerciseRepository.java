package gym.reps.counter.database.repository;

import gym.reps.counter.model.entity.WorkoutExercise;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutExerciseRepository extends CrudRepository<WorkoutExercise, Long> {
}
