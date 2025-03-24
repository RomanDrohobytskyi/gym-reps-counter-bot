package gym.reps.counter.database.repository;

import gym.reps.counter.model.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
}
