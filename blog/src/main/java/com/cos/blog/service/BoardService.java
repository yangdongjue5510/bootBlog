package com.cos.blog.service;

import com.cos.blog.model.Board;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


//스프링이 컴포넌트 스캔을 통해서 Bean에 자동 등록. IoC를 해줌
@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;
    @Transactional
    public void writePost(Board board, User user){
        board.setCount(0);
        board.setUser(user);
        boardRepository.save(board);
    }

    public Page<Board> postList(Pageable pageable) {
        return boardRepository.findAll(pageable);
    } //찾아서 페이지에 맞도록 분류하고 페이지 객체로 반환
}

