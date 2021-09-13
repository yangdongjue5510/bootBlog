package com.cos.blog.repository;

import com.cos.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//자동으로 bean으로 등록
//@Repository 생략가능
public interface UserRepository extends JpaRepository<User, Integer> {
    //기본적인 CRUD는 모두 가지고 있음.

    //JPA Naming query
    //SELECT * FROM user WHERE username = ? AND password = ?
    User findByUsernameAndPassword(String username, String password);

    @Query(value = "SELECT * FROM user WHERE username = ?1 AND password = ?2", nativeQuery = true)
    User login2(String username, String password);
}
