package com.cos.blog.config;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.config.auth.PrincipalDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration  //빈 등록(Ioc)
@EnableWebSecurity //시큐리티 필터를 등록. 필터 설정을 메소드에서
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정 주소를 접근하면 권한/인증을 미리 체크하겠다는 의
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalDetailService principalDetailService;

    @Bean //IoC / BCryptPasswordEncoder객체를 스프링이 관리
    public BCryptPasswordEncoder encodePWD(){
        return new BCryptPasswordEncoder();
    }
    //password를 어떻게 해쉬화했는지를 알려줘야 DB의 비번과 로그인시 비번을 비교가능!
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() //csrf 토큰 비활성화
                .authorizeRequests()//요청이 왔을 때
                    .antMatchers("/","/auth/**","/js/**","/css/**","/image/**") // /auth로 시작하는건
                    .permitAll() //모두 접근 가능
                    .anyRequest() //이 외의 다른 요청은
                    .authenticated() // 인증해야 접근 가능
                .and() // 인증이 필요한 경우
                    .formLogin() // 로그인 할 수 있도록 한다.
                    .loginPage("/auth/loginForm") //이 로그인 페이지에서
                    .loginProcessingUrl("/auth/loginProc") //시큐리티가 해당 주소로 오는  로그인 가로채고 수행
                    .defaultSuccessUrl("/"); //요청이 성공하고 나서 해당 주소로 이동.
                    //.failureUrl("/auth/loginForm");//실패시 해당 주소로 이동.
    }
}
