package com.cos.blog.service;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


//스프링이 컴포넌트 스캔을 통해서 Bean에 자동 등록. IoC를 해줌
@Service
@RequiredArgsConstructor
public class BoardService {
/*@Autowired가 많으면 해야 할 일.
    여러 DI된 객체들은
    public BoardService(DI된 객체, DI된 객체....) 이런 생성자를 부르는 것과 같다.
    그런데 매 DI객체마다 @Autowired하면 좀 지저분하니까...
    각 객체를 final처리하고 본 클래스에 @RequiredArgsConstructor를 붙인다.
    이 어노테이션은 초기화가 되지 않은 값을 생성자를 통해서 초기화 해달라는거다.
    즉 @Autowired를 일일히 붙이는 것과 같은 역할.
 */

    private final BoardRepository boardRepository;

    private final ReplyRepository replyRepository;


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

    @Transactional
    public void saveReply(ReplySaveRequestDto replySaveRequestDto){
        int userId = replySaveRequestDto.getUserId();
        int boardId = replySaveRequestDto.getBoardId();
        String content = replySaveRequestDto.getContent();
        replyRepository.mSave(userId, boardId, content);
    }

    @Transactional
    public void deleteReply(int replyId){
        replyRepository.deleteById(replyId);
    }
}

