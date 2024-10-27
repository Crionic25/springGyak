package hu.sajat.springgyakorlas.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationError>> handleValidationException(MethodArgumentNotValidException exception) {
        List<ValidationError> validationErrors = exception.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        validationErrors.forEach(validationError ->
            log.error("Error in validation: " + validationError.getField() + ": " + validationError.getErrorMessage())
        );
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountNotFoundByIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ValidationError> handleAccountNotFoundByIdException(AccountNotFoundByIdException exception) {
        ValidationError validationError = new ValidationError("accountId",
                "no account found with id: " + exception.getId());
        log.error("Error in validation: " + validationError.getField() + ": " + validationError.getErrorMessage());
        return List.of(validationError);
    }

    @ExceptionHandler(AccountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleAccountException(AccountException exception){

        log.error(exception.getField() + "  " + exception.getErrorMessage());
        return new ExceptionResponse(exception.getField(), exception.getErrorMessage());
    }

    @ExceptionHandler(TransferException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleTransferException(TransferException exception){

        log.error(exception.getField() + "  " + exception.getErrorMessage());
        return new ExceptionResponse(exception.getField(), exception.getErrorMessage());
    }
    @ExceptionHandler(LimitException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleLimitException(LimitException limitException){

        log.error(limitException.getField() + "  " + limitException.getErrorMessage());
        return new ExceptionResponse(limitException.getField(), limitException.getErrorMessage());
    }


}