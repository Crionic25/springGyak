package hu.sajat.springgyakorlas.dto;


import hu.sajat.springgyakorlas.model.Currency;
import hu.sajat.springgyakorlas.model.GoalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class GoalDto {

    private Long id;

    private AccountDto accountDto;

    private String goalName;

    private String story;

    private Double targetAmount;

    private Double receivedAmount;

    private Currency currency;

    private LocalDateTime createdDate;

    private LocalDateTime expDate;

    private boolean approved;

    private boolean successful;

    private GoalType goalType;

    private List<TransferDto> transfers;
}
