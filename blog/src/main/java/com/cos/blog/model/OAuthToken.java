package com.cos.blog.model;

import lombok.Data;

@Data
public class OAuthToken { //받은 토큰의 바디값을 변수명까지 그대로
    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private int refresh_token_expires_in;
}
