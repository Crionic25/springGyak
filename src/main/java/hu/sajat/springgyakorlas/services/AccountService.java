package hu.sajat.springgyakorlas.services;

import hu.sajat.springgyakorlas.dto.AccountDto;
import hu.sajat.springgyakorlas.dto.AccountRegistrationDto;

import java.util.List;

public interface AccountService  {
    void createAccount(AccountRegistrationDto accountRegistrationDto);

    AccountDto getAccount(String email);

    List<AccountDto> findAll();
}
