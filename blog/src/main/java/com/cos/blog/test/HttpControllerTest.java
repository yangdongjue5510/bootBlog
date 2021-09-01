package com.cos.blog.test;

import org.springframework.web.bind.annotation.*;

@RestController
public class HttpControllerTest {

    private static final String TAG = "HTTP Controller TEST : ";

    public String lombokTest(){
        Member m1 = new Member(1, "yang", "1234","email@com." );
        System.out.println(TAG+" getter : "+ m1.getId());
        m1.setId(5000);
        System.out.println(TAG+" setter : "+ m1.getId());

        return "lombok test completed!";
    }
    @GetMapping("/http/get")
    public String getTest(Member m){
        System.out.println(lombokTest());
        return "get id :" + m.getId() + ", username: "+m.getUsername()+", password : "+m.getPassword()+", email : "+m.getEmail();
    }
    @PostMapping("/http/post")
    public String postTest(@RequestBody Member m){
// public String postTest(@RequestBody String text){
        return "post id :" + m.getId() + ", username: "+m.getUsername()+", password : "+m.getPassword()+", email : "+m.getEmail();
    }
    @PutMapping("/http/put")
    public String putTest(@RequestBody Member m){
        return "put id :" + m.getId() + ", username: "+m.getUsername()+", password : "+m.getPassword()+", email : "+m.getEmail();
    }
    @DeleteMapping("/http/delete")
    public String deleteTest(){
        return "delete";
    }
}