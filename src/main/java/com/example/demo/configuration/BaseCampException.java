package com.example.demo.configuration;

import com.example.demo.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class BaseCampException extends RuntimeException{

    private final ErrorCode errorCode;
    private Throwable throwable;

    public BaseCampException(ErrorCode errorCode, Throwable throwable){
        this.errorCode = errorCode;
        this.throwable = throwable;
    }

    public BaseCampException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }
}
