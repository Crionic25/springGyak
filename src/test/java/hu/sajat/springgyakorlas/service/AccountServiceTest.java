package hu.sajat.springgyakorlas.service;

import hu.sajat.springgyakorlas.dto.AccountDto;
import hu.sajat.springgyakorlas.dto.AccountRegistrationDto;
import hu.sajat.springgyakorlas.exception.AccountException;
import hu.sajat.springgyakorlas.model.Account;
import hu.sajat.springgyakorlas.model.Currency;
import hu.sajat.springgyakorlas.repository.AccountRepository;
import hu.sajat.springgyakorlas.services.impl.AccountServiceImpl;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    private AccountServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new AccountServiceImpl(accountRepository);
    }

    @Test
    void createAccount() {
        AccountRegistrationDto accountRegistrationDto = new AccountRegistrationDto("Béla", "bela888@gmail.com", "password", Currency.AMD);
        when(accountRepository.existsByEmail(accountRegistrationDto.getEmail())).thenReturn(false);
        ArgumentCaptor<Account> argumentCaptor = ArgumentCaptor.forClass(Account.class);

        underTest.createAccount(accountRegistrationDto);

        verify(accountRepository).save(argumentCaptor.capture());
        assertEquals("Béla", argumentCaptor.getValue().getName());
    }
    @Test
    void getAccountOk() {
        String findUserByGiveEmail = "bela@gmail.com";
        when(accountRepository.findAccountByEmail(findUserByGiveEmail)).thenReturn(Optional.ofNullable(Account.builder().name("Béla").email("bela@gmail.com").build()));

        AccountDto accountDto = underTest.getAccount(findUserByGiveEmail);

        assertEquals("Béla", accountDto.getName());

    }
    @Test
    void getAccountButItsNotRegistered() {
        String findUserByGiveEmail = "bela@gmail.com";

        try {
            underTest.getAccount(findUserByGiveEmail);

        } catch (AccountException exception) {
            assertEquals("wrong email address", exception.getErrorMessage());
        }

    }
}
