package org.denis.coinkeeper.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.View;

import java.util.List;



@ControllerAdvice
public class ValidationExceptionHandler {

    private final View error;

    public ValidationExceptionHandler(View error) {
        this.error = error;
    }
    @ExceptionHandler(ServerErrorException.class)
    protected ResponseEntity<ErrorDto> handleServerErrorException(ServerErrorException ex) {

        return ResponseEntity.internalServerError()
                .body(new ErrorDto(List.of(ex.getMessage())));
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ErrorDto> handleBadRequestException(BadRequestException ex) {

        return ResponseEntity.badRequest()
                .body(new ErrorDto(List.of(ex.getMessage())));
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handle(MethodArgumentNotValidException ex) {
        final List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDto(errors));
    }
}