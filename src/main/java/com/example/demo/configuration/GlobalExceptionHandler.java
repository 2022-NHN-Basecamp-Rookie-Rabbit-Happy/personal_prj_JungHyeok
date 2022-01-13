package com.example.demo.configuration;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = BaseCampException.class)
    protected ResponseEntity<ErrorResponse> handleBaseCampException(BaseCampException baseCampException){
        return ErrorResponse.of(baseCampException.getErrorCode());
    }
}
