package com.cos.blog.controller.api;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private AuthenticationManager authenticationManager;

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
        //세션 속 시큐리티 컨텍스트에 Authenfication 객체가 들어가면 그 때부터 세션 정보가 활성화 된 것!(이미지 참고)
        //http요청이 오면 , authentication filter가 UsernamePasswordAuthenticationToken을 만든다!
        //AuthenticationManager가 위 토큰객체를 받아서 Authentication 객체를 만들어 세션을 만든다.

        //AuthenticationManager가 어떻게 Authentication객체는 만드냐?
        // 유저이름을 UserDetailService에 전달해서 DB에 있는지 확인한다.
        // 있으면 매니저가 비밀번호를 암호기준에 맞게 암호화하고 DB와 비교한다.
        // 비밀번호도 DB에 있으면 세션 속 시큐리티 컨텍스트 안에 Authentication 객체를 만들어넣는다.

        // 직접 Authentication 객체 만들어서 하는건 안됨~
        //매니저에 접근해서 해보자.

        //세션 등록
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
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
