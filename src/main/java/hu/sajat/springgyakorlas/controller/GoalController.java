package hu.sajat.springgyakorlas.controller;

import hu.sajat.springgyakorlas.dto.GoalCreationDto;
import hu.sajat.springgyakorlas.dto.GoalDto;
import hu.sajat.springgyakorlas.services.GoalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/goals")
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createGoal(@RequestBody GoalCreationDto goalCreationDto) {

        log.info("Http request POST / /goal/created with data: " + goalCreationDto);
        goalService.createGoal(goalCreationDto);
    }
    @GetMapping
    public List<GoalDto> getGoal(){
        return goalService.getAllGoal();
    }
    @DeleteMapping("/{id}")
    public void deleteGoal(@PathVariable long id) {
        goalService.deleteGoalById(id);
    }
}
