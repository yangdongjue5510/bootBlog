package com.cos.blog.controller;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping({"/", ""})
    public String index(Model model, @PageableDefault(size=3, sort = "id", direction = Sort.Direction.DESC)Pageable pageable){
        //스프링에서 데이터를 가져올 땐 Model이 필요!! & 페이지 별로 받기 위해 페이지 기준을 설정하는 pageable!
        //URI에 ?page=숫자 로 페이지별 이동 가능!
        model.addAttribute("boards", boardService.postList(pageable));
        return "index"; //그냥 컨트롤러는 return시 viewResolver가 작동! -> 해당 인덱스 페이지로 모델의 정보를 들고 이동!
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
