package hu.sajat.springgyakorlas.dto;


import hu.sajat.springgyakorlas.model.Currency;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class AccountRegistrationDto {

    @NotBlank
    private String name;
    @Email
    private String email;
    private String password;
    private Currency currency;


}
