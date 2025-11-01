package com.catsoft.demo.icecreamparlor;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Request arguments not valid")
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidation(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Request body not readable")
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void handleMessageNotReadable(HttpMessageNotReadableException e) {
        IO.println(e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Request body not readable")
    @ExceptionHandler(MismatchedInputException.class)
    public void handleMismatchedInput(MismatchedInputException e) {
        IO.println(e.getMessage());
    }
}
