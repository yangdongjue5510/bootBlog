package com.cos.blog.repository;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    //nativeQuary로 하기
    //인수로 전달된 순서대로 ?1 ?2 ?3에 들어가서 쿼리가 실행된다~
    @Modifying //javax transaction. select가 아닌 쿼리에 붙여주자.
    @Query(value="INSERT INTO reply(userId, boardId, content, createDate) VALUES(?1, ?2, ?3, now())", nativeQuery = true)
    int mSave(int userId, int boardId, String content); //업데이트 할 경우 영향받은 행의 갯수를 리턴해줌.
}
