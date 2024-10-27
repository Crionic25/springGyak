package hu.sajat.springgyakorlas.dto;


import hu.sajat.springgyakorlas.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class AccountDto {

    private Long id;
    private String name;
    private String email;

    private LocalDateTime registrationDate;

    private LocalDateTime lastEntryDate;

    private String level;

    private Currency currency;

    private List<GoalDto> goals;

    private List<TransferDto> transfers;

}
