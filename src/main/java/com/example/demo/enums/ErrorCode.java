package com.example.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    REQUIRED_TYPE_NOT_IMAGE(HttpStatus.BAD_REQUEST," 요청한 파일의 이미지 타입이 image가 아닙니다"),

    GUESTBOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "방명록이 존재하지 않습니다."),
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글이 존재하지 않습니다."),
    REPLY_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글이 존재하지 않습니다"),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "리뷰가 존재하지 않습니다"),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"처리과정중에 서버 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}
