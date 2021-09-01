package com.cos.blog.test;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyControllerTest {

    @Autowired//해당 클래스가 메모리에 올려지면, 해당 변수 타입에 맞는 객체가 메모리에 있으면 변수에 할당.(DI, 의존성 주입)
    private UserRepository userRepository;

    @PostMapping("/dummy/join")
    public String join(String username, String password, String email){
        //x-www.form-urelencoded는 key-value형태로 받는데 이는 스프링이 함수의 파라미터로 밸류값을 다 저장해준다.
        //@RequestParam("username") String u...이런식으로 변수명을 유연하게도 가능!
        System.out.println("username: "+username);
        System.out.println("password: "+password);
        System.out.println("email: "+email);
        return "회원가입이 완료 되었습니다!";
    }
    @PostMapping("/dummy/join2")
    public String join2(User user){
        //객체 형태로도 가능하다!(다만 데이터의 키값이 객체의 변수명과 일치해야 함
        System.out.println("role: "+user.getRole());
        System.out.println("username: "+user.getUsername());
        System.out.println("password: "+user.getPassword());
        System.out.println("email: "+user.getEmail());
        System.out.println("createDate: "+user.getCreateDate());

        user.setRole(RoleType.USER); //User 클래스의 @DynamicInsert를 대신하는 문장.
        userRepository.save(user); //회원정보 저장.
        return "회원가입이 완료 되었습니다!";
    }
}
