package com.cos.blog.controller.api;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class UserApiController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/user")
    public ResponseDto<Integer> save(@RequestBody User user){
        System.out.println("UserApiController save called");
        user.setRole(RoleType.USER);
        userService.signUp(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);//정상작동을 알림
    }

    @PostMapping("/api/user/login")
    //전통적 방법.
    public ResponseDto<Integer> login(@RequestBody User user, HttpSession session){
        System.out.println("UserApicontroller : login called");
        User principal =userService.login(user);
        if (principal != null){
            session.setAttribute("principal", principal);
        }
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}
