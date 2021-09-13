package com.cos.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration  //빈 등록(Ioc)
@EnableWebSecurity //시큐리티 필터를 등록. 필터 설정을 메소드에서
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정 주소를 접근하면 권한/인증을 미리 체크하겠다는 의
public class SecurityConfig  extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()//요청이 왔을 때
                    .antMatchers("/auth/**") // /auth로 시작하는건
                    .permitAll() //모두 접근 가능
                    .anyRequest() //이 외의 다른 요청은
                    .authenticated() // 인증해야 접근 가능
                .and() // 인증이 필요한 경우
                    .formLogin() // 로그인 할 수 있도록 한다.
                    .loginPage("/auth/loginForm"); //이 로그인 페이지에서
    }
}
