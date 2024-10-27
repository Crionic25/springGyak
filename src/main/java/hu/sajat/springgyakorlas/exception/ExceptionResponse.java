package hu.sajat.springgyakorlas.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExceptionResponse {

    private String field;
    private String errorMessage;
}
