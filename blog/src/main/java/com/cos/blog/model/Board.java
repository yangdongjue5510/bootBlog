package com.cos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

//lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob //대용량 데이터를 다룰 때 사용
    private String content; //섬머노트 라이브러리 <html>태그가 섞여서 디자인이 된다.

    @ColumnDefault("0") //default value
    private int count;

    //자바에서 객체를 저장하면, DB에서는 FK로 구현된다.
    //객체를 다루려면, 관계를 설정해줘야 한다.
    @ManyToOne(fetch = FetchType.EAGER) //Many = Board, One = User 한 유저가 여러 게시물을 작성 가능한 관계.
    //fetch 전략이 해당 클래스를 가져오면 반드시 가져온다.(게시물을 불러오면 user를 반드시 조인해서 불러와라!)
    @JoinColumn(name = "userId")//데이터베이스는 객체를 저장할 수 없으므로 userId라는 값이 대신 테이블에 저장된다.
    private User user; //DB에서는 오브젝트를 저장할 수 없다. 그래서 FK를 사용. 반면 자바는 오브젝트를 저장할 수 있다.

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
    //LAZY : board를 불러온다고 무조건 가져올 필요는 없다!(기본값으로 설정되어잇음)
    //EAGER : 게시물이 불러오면 무조건 조인해서 가져와야됨!
    private List<Reply> reply;

    @CreationTimestamp
    private Timestamp createDate;
}
