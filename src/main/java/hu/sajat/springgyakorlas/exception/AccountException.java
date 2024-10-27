package hu.sajat.springgyakorlas.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AccountException extends RuntimeException{

    private final String field;
    private final String errorMessage;

}
