package hu.sajat.springgyakorlas.service;

import hu.sajat.springgyakorlas.dto.RegisterTransferDto;
import hu.sajat.springgyakorlas.dto.TransferDto;
import hu.sajat.springgyakorlas.model.Account;
import hu.sajat.springgyakorlas.model.Currency;
import hu.sajat.springgyakorlas.model.Goal;
import hu.sajat.springgyakorlas.model.Transfer;
import hu.sajat.springgyakorlas.repository.AccountRepository;
import hu.sajat.springgyakorlas.repository.GoalRepository;
import hu.sajat.springgyakorlas.repository.TransferRepository;
import hu.sajat.springgyakorlas.services.impl.TransferServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransferServiceTest {
    @Mock
    private TransferRepository transferRepository;
    @Mock
    private GoalRepository goalRepository;
    @Mock
    private AccountRepository accountRepository;

    private TransferServiceImpl underTest;
    @BeforeEach
    void setUp() {
        underTest = new TransferServiceImpl(transferRepository, goalRepository,  accountRepository);
    }
    @Test
    void createTransfer() {
        RegisterTransferDto registerTransferDto = RegisterTransferDto.builder().goalId(1L).amount(20.0).accountEmail("bela@gmail.com").build();
        when(accountRepository.findAccountByEmail(registerTransferDto.getAccountEmail())).thenReturn(Optional.ofNullable(Account.builder().name("Béla").email("bela@gmail.com").currency(Currency.valueOf("USD")).build()));
        when(goalRepository.findById(registerTransferDto.getGoalId())).thenReturn(Optional.ofNullable(Goal.builder().goalName("valami").id(1L).currency(Currency.valueOf("USD")).build()));
        ArgumentCaptor<Transfer> argumentCaptor = ArgumentCaptor.forClass(Transfer.class);

        underTest.saveTransfer(registerTransferDto);

        verify(transferRepository).save(argumentCaptor.capture());
        assertEquals("valami", argumentCaptor.getValue().getGoal().getGoalName());
        assertEquals("Béla", argumentCaptor.getValue().getAccount().getName());
        assertEquals(20, argumentCaptor.getValue().getAmountCurrency());

    }
    @Test
    void allTransfer() {
        when(transferRepository.findAll()).thenReturn(List.of(Transfer.builder().id(1L).
                account(Account.builder().name("Béla").email("bela@gmail.com").build())
                .goal(Goal.builder().goalName("valami").id(1L).build()).approved(true).build()));

        List<TransferDto> transferDtos = underTest.findAll();

        assertEquals(1, transferDtos.size());
    }
}
