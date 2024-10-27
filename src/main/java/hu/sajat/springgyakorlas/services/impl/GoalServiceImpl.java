package hu.sajat.springgyakorlas.services.impl;

import hu.sajat.springgyakorlas.dto.AccountDto;
import hu.sajat.springgyakorlas.dto.GoalCreationDto;
import hu.sajat.springgyakorlas.dto.GoalDto;
import hu.sajat.springgyakorlas.exception.AccountException;
import hu.sajat.springgyakorlas.model.Account;
import hu.sajat.springgyakorlas.model.Goal;
import hu.sajat.springgyakorlas.repository.AccountRepository;
import hu.sajat.springgyakorlas.repository.GoalRepository;
import hu.sajat.springgyakorlas.services.GoalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class GoalServiceImpl implements GoalService {
    private final GoalRepository goalRepository;
    private final AccountRepository accountRepository;
    @Override
    public void createGoal(GoalCreationDto goalCreationDto) {
        Goal goal = new Goal();
        Account account = accountRepository.findAccountByEmail(goalCreationDto.getAccountEmail())
                .orElseThrow(() -> new AccountException("creategoal", "registration required"));


        goal.setAccount(account);
        goal.setGoalName(goalCreationDto.getGoalName());
        goal.setStory(goalCreationDto.getStory());
        goal.setGoalType(goalCreationDto.getGoalType());
        goal.setCurrency(goalCreationDto.getCurrency());
        goal.setReceivedAmount(0.0);
        goal.setTargetAmount(goalCreationDto.getTargetAmount());
        goalRepository.save(goal);

    }

    @Override
    public List<GoalDto> getAllGoal() {
        return StreamSupport.stream(goalRepository.findAll().spliterator(), false).
                map(g -> GoalDto.builder()
                        .id(g.getId())
                        .goalName(g.getGoalName())
                        .accountDto(AccountDto.builder()
                                .id(g.getAccount().getId())
                                .name(g.getAccount().getName())
                                .email(g.getAccount().getEmail())
                                .build())
                        .story(g.getStory())
                        .targetAmount(g.getTargetAmount())
                        .receivedAmount(g.getReceivedAmount())
                        .currency(g.getCurrency())
                        .createdDate(g.getCreatedDate())
                        .expDate(g.getExpDate())
                        .approved(g.isApproved())
                        .successful(g.isSuccessful())
                        .goalType(g.getGoalType())
                        .build()).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteGoalById(long id) {
        goalRepository.deleteGoalById(id);
    }

}
