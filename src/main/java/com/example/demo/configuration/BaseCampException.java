package com.example.demo.configuration;

import com.example.demo.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BaseCampException extends RuntimeException{

    private final ErrorCode errorCode;


}
