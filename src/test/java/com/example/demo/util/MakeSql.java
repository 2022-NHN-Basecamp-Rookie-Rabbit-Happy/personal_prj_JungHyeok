package com.example.demo.util;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class MakeSql {

    @Test
    @DisplayName("300개의 더미데이터 테스를 만들 파일 입출력을 작성한다")
    @Disabled
//  주석주의
//INSERT INTO guest_book (gno,moddate,regdate,content,title,writer) VALUE (1, NOW(), NOW(), 'Content...1', 'Title...1', 'user1');
    void makeSql() throws FileNotFoundException {
        try(PrintWriter pw=new PrintWriter("src/main/resources/dummy.sql")){

            for(int i=1; i<=300; i++){
                String data = "INSERT INTO guest_book (gno, moddate, regdate, content, title, writer) VALUES (" + i + ", NOW(), NOW(), 'Content..." + i + "', 'Title..."+i+"', " + "'user" + i%10 + "')";
//                String data = "INSERT INTO guest_book (gno) VALUE (" + i + ")";
                pw.println(data);
            }
        }
    }
}
