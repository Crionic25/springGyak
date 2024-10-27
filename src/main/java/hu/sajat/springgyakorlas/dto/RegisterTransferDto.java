package hu.sajat.springgyakorlas.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;



@Data
@AllArgsConstructor
@Builder
public class RegisterTransferDto {

    private String accountEmail;
    @NotBlank
    private Long goalId;
    @Min(1)
    private Double amount;
}
