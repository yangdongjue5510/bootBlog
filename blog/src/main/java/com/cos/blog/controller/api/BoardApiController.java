package com.cos.blog.controller.api;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import com.cos.blog.service.BoardService;
import com.cos.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class BoardApiController {
    @Autowired
    private BoardService boardService;

    @PostMapping("/api/board")
    public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal){
        boardService.writePost(board, principal.getUser());

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);//정상작동을 알림
    }

    @DeleteMapping("api/board/{id}")
    public ResponseDto<Integer> deleteById(@PathVariable int id){
        boardService.deletePost(id);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);//정상작동을 알림
    }

    @PutMapping("/api/board/{id}")
    public ResponseDto<Integer> update(@PathVariable int id , @RequestBody Board board){
        boardService.updatePost(id, board);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);//정상작동을 알림
    }
}
