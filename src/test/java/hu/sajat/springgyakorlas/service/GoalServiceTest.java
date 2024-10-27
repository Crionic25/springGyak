package hu.sajat.springgyakorlas.service;

import hu.sajat.springgyakorlas.dto.GoalCreationDto;
import hu.sajat.springgyakorlas.exception.AccountException;
import hu.sajat.springgyakorlas.model.Account;
import hu.sajat.springgyakorlas.model.Goal;
import hu.sajat.springgyakorlas.model.GoalType;
import hu.sajat.springgyakorlas.repository.AccountRepository;
import hu.sajat.springgyakorlas.repository.GoalRepository;
import hu.sajat.springgyakorlas.services.impl.GoalServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GoalServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private GoalRepository goalRepository;

    private GoalServiceImpl underTest;

    @BeforeEach
    void setUp() {

        underTest = new GoalServiceImpl(goalRepository, accountRepository);
    }
    @Test
    void createGoalOk() {

        GoalCreationDto goalCreationDto = GoalCreationDto.builder().goalName("Goal name").story("The story").accountEmail("bela@gmail.com").goalType(GoalType.NONPROFIT).targetAmount(2000.0).build();
        when(accountRepository.findAccountByEmail(goalCreationDto.getAccountEmail())).thenReturn(Optional.ofNullable(Account.builder().name("Béla").email("bela@gmail.com").goals(List.of(Goal.builder().goalName("Segítség").build())).build()));

        ArgumentCaptor<Goal> argumentCaptor = ArgumentCaptor.forClass(Goal.class);

        underTest.createGoal(goalCreationDto);

        verify(goalRepository).save(argumentCaptor.capture());
        assertEquals("Goal name", argumentCaptor.getValue().getGoalName());
        assertEquals("Béla", argumentCaptor.getValue().getAccount().getName());

    }
    @Test
    void createGoalWithEmailNotRegistered() {

        GoalCreationDto goalCreationDto = GoalCreationDto.builder().goalName("Goal name").story("The story").accountEmail("bela@gmail.com").goalType(GoalType.NONPROFIT).targetAmount(2000.0).build();

        try {
            underTest.createGoal(goalCreationDto);

        } catch (AccountException exception) {
            assertEquals("registration required", exception.getErrorMessage());

        }

    }

}
