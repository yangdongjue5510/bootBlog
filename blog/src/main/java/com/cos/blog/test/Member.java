package com.cos.blog.test;

import lombok.*;

//data=getter + setter
//AllArgsConstructor 모든 원소들을 입력받는 생성자
//@RequiredArgsConstructor = final 키워드 붙은 멤버들만 입력받는 생성자 만듬
//@NoArgsConstructor = 빈 생성자 만듬
@Data
@AllArgsConstructor
@NoArgsConstructor
//@RequiredArgsConstructor
public class Member {
    private int id;
    private String username;
    private String password;
    private String email;
}
