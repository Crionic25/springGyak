package hu.sajat.springgyakorlas.dto;


import hu.sajat.springgyakorlas.model.Currency;
import hu.sajat.springgyakorlas.model.GoalType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoalCreationDto {
    private String accountEmail;
    @NotBlank
    @Size(min = 5, max = 50)
    private String goalName;
    @NotBlank
    @Size(min = 200, max = 2000)
    private String story;
    @NotBlank
    private GoalType goalType;
    @NotBlank
    private Currency currency;
    @Min(0)
    private Double targetAmount;
}
