package com.example.demo.dto;

import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@ToString
public class SampleDTO {

    private Long sno;

    private String first;

    private String last;

    private LocalDateTime regTime;

    public SampleDTO(Long sno, String first, String last, LocalDateTime regTime) {
        this.sno = sno;
        this.first = first;
        this.last = last;
        this.regTime = regTime;
    }

    public Long getSno() {
        return sno;
    }
}
