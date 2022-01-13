package com.example.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    GUESTBOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "방명록이 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}
