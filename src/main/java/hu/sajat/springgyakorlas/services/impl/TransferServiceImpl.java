package hu.sajat.springgyakorlas.services.impl;

import hu.sajat.springgyakorlas.dto.RegisterTransferDto;
import hu.sajat.springgyakorlas.dto.SummaryDataDto;
import hu.sajat.springgyakorlas.dto.SummaryDto;
import hu.sajat.springgyakorlas.dto.TransferDto;
import hu.sajat.springgyakorlas.exception.AccountException;
import hu.sajat.springgyakorlas.exception.AccountNotFoundByIdException;
import hu.sajat.springgyakorlas.exception.LimitException;
import hu.sajat.springgyakorlas.model.Account;
import hu.sajat.springgyakorlas.model.Goal;
import hu.sajat.springgyakorlas.model.GoalType;
import hu.sajat.springgyakorlas.model.Transfer;
import hu.sajat.springgyakorlas.repository.AccountRepository;
import hu.sajat.springgyakorlas.repository.GoalRepository;
import hu.sajat.springgyakorlas.repository.TransferRepository;
import hu.sajat.springgyakorlas.services.ExchangeService;
import hu.sajat.springgyakorlas.services.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
    private final TransferRepository transferRepository;
    private final GoalRepository goalRepository;
    private final AccountRepository accountRepository;
    @Override
    public List<TransferDto> findAll() {
        return StreamSupport.stream(transferRepository.findAll().spliterator(), false)
                .map(p -> TransferDto.builder()
                        .id(p.getId())
                        .goal(p.getGoal().getId())
                        .account(p.getAccount().getId())
                        .amount(p.getAmount())
                        .createdDate(p.getCreatedDate())
                        .amountCurrency(String.valueOf(p.getAmountCurrency()))
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public void saveTransfer(RegisterTransferDto transferDto) {
        Transfer transfer = new Transfer();
        ExchangeService exchangeService = new ExchangeService();
        Double amountInEur = 0.0;
        Double amountInCurr = 0.0;

        Account account = accountRepository.findAccountByEmail(transferDto.getAccountEmail())
                .orElseThrow(() -> new AccountException("Savetransfer", "Account not found with email: " + transferDto.getAccountEmail()));

        Goal goal = goalRepository.findById(transferDto.getGoalId())
                .orElseThrow(() -> new AccountNotFoundByIdException(transferDto.getGoalId()));


        if (!account.getCurrency().toString().equals("EUR")) {
            amountInEur = exchangeService.calculateCurrencyToEur(account.getCurrency().toString(), transferDto.getAmount());
        } else {
            amountInEur = transferDto.getAmount();
        }

        if (!goal.getCurrency().toString().equals("EUR")) {
            amountInCurr = exchangeService.calculateEurToCurrency(goal.getCurrency().toString(), amountInEur);
        } else {
            amountInCurr = transferDto.getAmount();
        }

        transfer.setAccount(account);
        transfer.setGoal(goal);
        transfer.setAmount(amountInEur);
        transfer.setAmountCurrency(amountInCurr);

        List<Transfer> findTransfer = transferRepository.findTransfersByApprovedIsFalseWhereAccountId(account.getId());

        if (findTransfer.isEmpty()) {
            transferRepository.save(transfer);
        } else {
            throw new AccountException("new transfer", "you already have an unconfirmed transfer");
        }
    }

    @Override
    public SummaryDataDto summary(SummaryDto summaryDto) {
        ExchangeService exchangeService = new ExchangeService();
        Double accountAmount, goalAmount = 0.0;
        Account account = accountRepository.findAccountByEmail(summaryDto.getAccountEmail())
                .orElseThrow(() -> new AccountException("Summary", "Account not found with email: " + summaryDto.getAccountEmail()));

        Goal goal = goalRepository.findById(summaryDto.getGoalId())
                .orElseThrow(() -> new AccountNotFoundByIdException(summaryDto.getGoalId()));

        summaryDto.setAmount(calculateAmountWithFee(goal.getGoalType(),summaryDto.getAmount()));

        if (!account.getCurrency().toString().equals("EUR")) {
            accountAmount = exchangeService.calculateEurToCurrency(account.getCurrency().toString(), summaryDto.getAmount());
        } else {
            accountAmount = summaryDto.getAmount();
        }
        if (!goal.getCurrency().toString().equals("EUR")) {
            goalAmount = exchangeService.calculateEurToCurrency(account.getCurrency().toString(), summaryDto.getAmount());
        } else {
            goalAmount = summaryDto.getAmount();
        }
        if(goalAmount < 5 ){
            throw new LimitException("goal amount", "There is a minimum limit for the amount set to 5 EUR.");
        }
        if(goalAmount >= (goal.getTargetAmount()*0.2) ){
            throw new LimitException("goal amount", "There is a maximum limit for the amount set to goal's target's 20%.");
        }

        return new SummaryDataDto(goal.getGoalName(), account.getCurrency().getCurrency(),accountAmount,  goal.getCurrency().getCurrency(), goalAmount);
    }
    private Double calculateAmountWithFee(GoalType goalType, double amount){
        double feeAdding = amount * goalType.transactionFee;
        return amount+feeAdding;
    }
}
