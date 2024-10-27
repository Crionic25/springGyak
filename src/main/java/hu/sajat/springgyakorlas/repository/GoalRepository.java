package hu.sajat.springgyakorlas.repository;

import hu.sajat.springgyakorlas.model.Goal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface GoalRepository extends CrudRepository<Goal, Long> {
    void deleteGoalById(long id);

}
