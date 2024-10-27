package hu.sajat.springgyakorlas.controller;

import hu.sajat.springgyakorlas.dto.AccountDto;
import hu.sajat.springgyakorlas.dto.AccountRegistrationDto;
import hu.sajat.springgyakorlas.services.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping(value = "/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAccount(@RequestBody @Valid AccountRegistrationDto accountRegistrationDto)  {

        accountService.createAccount(accountRegistrationDto);
        log.info("Account created with email: " + accountRegistrationDto.getEmail());
    }

    @GetMapping
    public List<AccountDto> getAccount() {

        return accountService.findAll();
    }
}
