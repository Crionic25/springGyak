package hu.sajat.springgyakorlas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SummaryDataDto {
    private String goalName;
    private String accountCurr;
    private Double accountAmount;
    private String goalCurr;
    private Double goalAmount;
}
