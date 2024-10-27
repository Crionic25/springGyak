package hu.sajat.springgyakorlas.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExchangeException  extends RuntimeException{
    private final String field;
    private final String errorMessage;
}
