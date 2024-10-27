package hu.sajat.springgyakorlas.services.impl;

import hu.sajat.springgyakorlas.dto.AccountDto;
import hu.sajat.springgyakorlas.dto.AccountRegistrationDto;
import hu.sajat.springgyakorlas.exception.AccountException;
import hu.sajat.springgyakorlas.model.Account;
import hu.sajat.springgyakorlas.repository.AccountRepository;
import hu.sajat.springgyakorlas.services.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public void createAccount(AccountRegistrationDto accountRegistrationDto) {

        if (accountRepository.existsByEmail(accountRegistrationDto.getEmail())) {
            throw new AccountException("registration", "this email address already registered");
        }

        Account newAccount = Account.builder()
                .name(accountRegistrationDto.getName())
                .email(accountRegistrationDto.getEmail())
                .password(accountRegistrationDto.getPassword())
                .level("ROLE_USER")
                .lastEntryDate(LocalDateTime.now())
                .currency(accountRegistrationDto.getCurrency())
                .build();
        accountRepository.save(newAccount);
    }

    @Override
    public AccountDto getAccount(String email) {
        Account account = accountRepository.findAccountByEmail(email).orElseThrow(() -> new AccountException("getAccount", "wrong email address"));

        return AccountDto.builder()
                .id(account.getId())
                .name(account.getName())
                .email(account.getEmail())
                .registrationDate(account.getRegistrationDate())
                .lastEntryDate(account.getLastEntryDate())
                .level(account.getLevel())
                .currency(account.getCurrency())
                .build();
    }

    @Override
    public List<AccountDto> findAll() {
        return StreamSupport.stream(accountRepository.findAll().spliterator(), false)
                .map(p -> AccountDto.builder()
                        .currency(p.getCurrency())
                        .email(p.getEmail())
                        .name(p.getName())
                        .registrationDate(p.getRegistrationDate())
                        .build()
                )
                .collect(Collectors.toList());
    }
}
