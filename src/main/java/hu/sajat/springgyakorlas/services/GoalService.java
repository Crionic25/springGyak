package hu.sajat.springgyakorlas.services;

import hu.sajat.springgyakorlas.dto.GoalCreationDto;
import hu.sajat.springgyakorlas.dto.GoalDto;
import hu.sajat.springgyakorlas.model.Goal;

import java.util.List;

public interface GoalService {
    void createGoal(GoalCreationDto goalCreationDto);

    List<GoalDto> getAllGoal();

    void deleteGoalById(long id);
}
