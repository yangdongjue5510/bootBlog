package com.cos.blog.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
//예외에 따른 안내 사이트를 호출하기 위해서 핸들러를 사용하자.
@ControllerAdvice //프로젝트 어디서든 예외 발생시 해당 클래스를 호출
@RestController
public class GlobalExceptionHandler {
    @ExceptionHandler(value = IllegalArgumentException.class) //특정 예외가 메소드에 걸리게 설정
    public String handlerArgumentException(IllegalArgumentException e){
        return "<h1>"+e.getMessage()+"</h1>";
    }
}
