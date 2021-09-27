package com.cos.blog.controller;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.SocketUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;


@Controller
public class UserController {
    //auth는 인증이 필요없이 방문할 수 있는 uri를 의미.
    //그냥 주소가 /이면 index.jsp허용
    //static 이하에 있는 js, css, image 허용
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Value("${kakao.key}")
    private String kakaoKey;

    @GetMapping("/auth/joinForm")
    public String joinForm(){
        return "/user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm(){
        return "/user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm(){
        return "user/updateForm";
    }

    @GetMapping("/auth/kakao/callback")
    public /*@ResponseBody*/ String kakaoCallback(String code){
        //일반 컨트롤러에서 Data를 리턴하는 함수-@ResponseBody

        //POST 방식으로 key=value 데이터를 요청(카카오로)
        //HttpsURLConnection을 이용할 수 있지만 라이브러리가 더 편함
        RestTemplate rt = new RestTemplate();

        //http 헤더 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        //http 바디 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "588c0de26db068ebeb3e6d00bb442aff");
        params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
        params.add("code", code);//code는 동적으로 받음

        //요청 http 객체에 헤더와 매개변수를 넣음.
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
        //요청 URI, 요청 메서드, 데이터와 헤더값, 응답받을 타입
        ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST, kakaoTokenRequest, String.class);
        //json을 자바에서 잘 다루기 위해서 ObjectMapper 라이브러리를 사용
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = null;
        try {
            oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("kakao access Token : "+oAuthToken.getAccess_token() );

        //토큰으로 사용자 정보 요청하기
        RestTemplate rt2 = new RestTemplate();

        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer "+oAuthToken.getAccess_token());
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);
        ResponseEntity<String> response2 = rt2.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST, kakaoProfileRequest, String.class);
        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("카카오 id : " + kakaoProfile.getId());
        System.out.println("카카오 이메일 : " + kakaoProfile.getKakao_account().getEmail());

        System.out.println("블로그 서버 유저네임 : "+ kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId());
        System.out.println("블로그 서버 이메일 : " + kakaoProfile.getKakao_account().getEmail());
        String kakaoPw = kakaoKey+kakaoProfile.getId();
        System.out.println("블로그 패스워드 : " + kakaoPw);

        //카카오 정보를 기반으로 회원 객체 만들기
        User kakaoUser = User.builder().username(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
                                                  .password(kakaoPw)
                                                  .email(kakaoProfile.getKakao_account().getEmail())
                                                  .oAuth("kakao")
                                                  .build();
        //기존 사용자 중에 해당 정보를 가진 유저가 없으면 회원가입처리
        User originUser = userService.findUser(kakaoUser.getEmail());
        if(originUser.getUsername()==null){
            System.out.println("카카오 회원 정보로 계정을 생성합니다.");
            userService.signUp(kakaoUser);
        }
        //이미 있을 경우 로그인 처리(세션을 등록)

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(),kakaoUser.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("카카오 소셜 로그인했습니다.");


        return  "redirect:/";
    }
}
