package hu.sajat.springgyakorlas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class TransferDto {

    private Long id;

    private Long account;

    private Long goal;

    private Double amount;

    private String amountCurrency;

    private LocalDateTime createdDate;
}

