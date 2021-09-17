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

    @Transactional(readOnly = true)
    public Page<Board> postList(Pageable pageable) {
        return boardRepository.findAll(pageable);
    } //찾아서 페이지에 맞도록 분류하고 페이지 객체로 반환

    @Transactional(readOnly = true)
    public Board postDetail(int id){
        return boardRepository.findById(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("글 상세보기 실패 : id를 찾지 못함");
                });
    }

    @Transactional
    public void deletePost(int id){
        boardRepository.deleteById(id);
    }

    @Transactional
    public void updatePost(int id, Board requestBoard){
        Board board = boardRepository.findById(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("글 수정 실패 : id를 찾지 못함");
                }); //영속성 로드
        board.setTitle(requestBoard.getTitle());
        board.setContent(requestBoard.getContent());
        //이 메소드가 종료될 때, 트랜잭션이 종료된다.
        // 더티체킹이 하여 변화된 값이 존재하면, 자동으로 DB에 플러시해서 업데이트한다.
    }
}

