package com.cos.blog.config.auth;

import com.cos.blog.model.User;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
@Getter
public class PrincipalDetail  implements UserDetails {
    private User user;
    public PrincipalDetail(User user){
        this.user = user;
    };

    //계정이 어떤 권한을 가졌는지.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(()->{return "ROLE_"+user.getRole();});//role 앞에 ROLE_붙이는건 규칙!!!
        return collectors;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    //계정이 만료됐는지 리턴(true: 만료안됨)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    //계정이 잠겼는지 리턴(true: 안잠김)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    //비밀번호가 만료됐는지 리턴(true: 만료안됨)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    //계정활성화
    @Override
    public boolean isEnabled() {
        return true;
    }
}
