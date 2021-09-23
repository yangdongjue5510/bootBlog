package com.cos.blog.controller.api;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class UserApiController {

    @Autowired
    private UserService userService;

    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody User user){
        System.out.println("UserApiController save called");
        userService.signUp(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);//정상작동을 알림
    }
    @PutMapping("/user")
    public ResponseDto<Integer> update(@RequestBody User user){
        userService.updateUser(user);
        //여기서 트랜잭션이 종료되서 DB는 변경, 하지만 세션은 반영 안됐음.
        //직접 세션 값을 변경.
        //시큐리티 컨텍스트에 Authenfication 객체가 들어가면 그 때부터 세션 정보가 활성화 된 것!(이미지 참고)
        //http요청으로 authentication filter가 처음 body에 아이디 비번을 파싱하고(?)
        //UsernamePasswordAuthenticationToken을 만든다!
        //AuthenticationManager가 위 토큰객체를 받아서 Authentication 객체를 만든다.
        //Authentication객체는 어떻게 만드냐? Authentication 객체를 UserDetailService에 전달한다.
        //UserDetailService는 유저네임을 가지고 DB에 해당 유저네임이 있는지 확인 -> 있으면 Authentication 객체 만듬

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
    //전통적 로그인 구현
//    @PostMapping("/api/user/login")
//    public ResponseDto<Integer> login(@RequestBody User user, HttpSession session){
//        System.out.println("UserApicontroller : login called");
//        User principal =userService.login(user);
//        if (principal != null){
//            session.setAttribute("principal", principal);
//        }
//        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
//    }


}
