package com.cos.blog.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;


@Controller
public class UserController {
    //auth는 인증이 필요없이 방문할 수 있는 uri를 의미.
    //그냥 주소가 /이면 index.jsp허용
    //static 이하에 있는 js, css, image 허용
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
    public @ResponseBody String kakaoCallback(String code){
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
        return "카카오 토큰 요청 완료"+response;
    }
}
