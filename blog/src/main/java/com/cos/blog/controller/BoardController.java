package com.cos.blog.controller;

import com.cos.blog.config.auth.PrincipalDetail;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @GetMapping({"/", ""})
    public String index(){
        return "index";
    }
    //컨트롤러에서는 어떻게 세션을 찾을까?
    //매개변수로 @AuthenticationPrincipal PrincipalDetail principal를 전달해주면됨
//    @GetMapping("/")
//    public String index2(@AuthenticationPrincipal PrincipalDetail principal) {
//        System.out.println("로그인한 사용자 아이디 "+principal.getUsername());
//        return "index";
//    }

    //USER 권한 필요
    @GetMapping("/board/saveForm")
    public String saveForm(){
        return "board/saveForm";
    }
}
