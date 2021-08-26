package com.cos.blog.test;

import org.springframework.web.bind.annotation.*;

@RestController
public class HttpControllerTest {

    @GetMapping("/http/get")
    public String getTest(){
        return "get";
    }
    @PostMapping("/http/post")
    public String postTest(){
        return "post";
    }
    @PutMapping("/http/put")
    public String putTest(){
        return "put";
    }
    @DeleteMapping("/http/delete")
    public String deleteTest(){
        return "delete";
    }
}
