package gym.reps.counter.database.repository;

import gym.reps.counter.model.entity.MuscleGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MuscleGroupRepository extends CrudRepository<MuscleGroup, Long> {
    List<MuscleGroup> findAll();
}
