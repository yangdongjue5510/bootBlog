package com.cos.blog.repository;

import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//자동으로 bean으로 등록
//@Repository 생략가능
public interface BoardRepository extends JpaRepository<Board, Integer> {
    //기본적인 CRUD는 모두 가지고 있음.

}
