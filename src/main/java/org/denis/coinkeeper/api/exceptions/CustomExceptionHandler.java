package org.denis.coinkeeper.api.exceptions;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ServerErrorException.class)
    protected ResponseEntity<ErrorDto> handleServerErrorException(ServerErrorException ex) {

        return ResponseEntity.internalServerError()
                .body(ErrorDto
                        .builder()
                        .error("Server error")
                        .errorDetails(List.of(ex.getMessage()))
                        .build()
                );
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ErrorDto> handleBadRequestException(BadRequestException ex) {

        return ResponseEntity.badRequest()
                .body(ErrorDto
                        .builder()
                        .error("Bad request")
                        .errorDetails(ex.getErrors().isEmpty() ? List.of(ex.getMessage()) : ex.getErrors())
                        .build()
                );

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        final List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto("Validation failure", errors));

    }
}