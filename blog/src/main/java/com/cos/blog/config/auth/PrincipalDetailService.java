package com.cos.blog.config.auth;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service //Bean등록
public class PrincipalDetailService implements UserDetailsService {
    //username과 password를 가로채서 처리할때
    //password는 알아서 처리하니까
    //username만 DB에 있는지 확인하면 된다.
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User principal = userRepository.findByUsername(username)
                .orElseThrow(()->{
                    return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다."+username);
                });
        return new PrincipalDetail(principal); //이때 시큐리티의 유저정보 저장됨! 유저정보가 없다면, user, 콘솔창 비번이 유저정보로 저장될 것이다.
    }
}
