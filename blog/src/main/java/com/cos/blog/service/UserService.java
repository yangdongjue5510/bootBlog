package com.cos.blog.service;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//스프링이 컴포넌트 스캔을 통해서 Bean에 자동 등록. IoC를 해줌
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional //메소드 속 여러 트랜잭션이 하나의 트랜잭션을 묶임
    public void signUp(User user){
        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword);
        user.setRole(RoleType.USER);
        user.setPassword(encPassword);
        userRepository.save(user);//잘못된 예외발생시 GlobalExceptionHandler가 예외처리
    }

    @Transactional
    public void updateUser(User user){
        //수정은 영속성 컨텍스트에 user오브젝트를 영속화 시키고, 영속화된 user 오브젝트를 수정하는 방법
        //select하면 user오브젝트가 영속화가 된다. -> 영속화된 오브젝트를 변경하면 DB에 flush!
        User persistence = userRepository.findById(user.getId()).orElseThrow(()->{
            return new IllegalArgumentException("회원찾기 실패");
        });
        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword);
        persistence.setPassword(encPassword);
        persistence.setEmail(user.getEmail());
        //트랜잭션 종료시 자동 커밋! (더티커밋!)
    }

//    @Transactional(readOnly = true)//select 할 때 트랜잭션 시작, 서비스 종료시까지 트랜잭션 종료(정합성)
//    public User login(User user){
//        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//    }
}

