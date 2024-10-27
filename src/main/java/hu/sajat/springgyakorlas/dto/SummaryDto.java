package hu.sajat.springgyakorlas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SummaryDto {
    private String accountEmail;
    private Long goalId;
    private Double amount;
}
