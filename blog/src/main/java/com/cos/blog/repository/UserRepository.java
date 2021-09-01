package com.cos.blog.repository;

import com.cos.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

//자동으로 bean으로 등록
//@Repository 생략가능
public interface UserRepository extends JpaRepository<User, Integer> {
    //기본적인 CRUD는 모두 가지고 있음.
}
