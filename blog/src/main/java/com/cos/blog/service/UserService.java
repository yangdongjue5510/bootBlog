package com.cos.blog.service;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//스프링이 컴포넌트 스캔을 통해서 Bean에 자동 등록. IoC를 해줌
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional //메소드 속 여러 트랜잭션이 하나의 트랜잭션을 묶임
    public void signUp(User user){
        userRepository.save(user);//잘못된 예외발생시 GlobalExceptionHandler가 예외처리
    }

    @Transactional(readOnly = true)//select 할 때 트랜잭션 시작, 서비스 종료시까지 트랜잭션 종료(정합성)
    public User login(User user){
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }
}

