package com.cos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

//lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity //User 클래스가 mysql에 테이블이 생성된다.
public class User {
    @Id//primary key
    @GeneratedValue(strategy =  GenerationType.IDENTITY) //넘버링 전략, 프로젝트에 연결된 db의 넘버링 전략을 따라(identity전략)
    private int id; //시퀀스, auto-increment

    @Column(nullable = false, length = 30)
    private String username;

    @Column(nullable = false, length =  100) //해쉬때문에 넉넉히 잡음
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    @ColumnDefault("'user'") //" 'user' "로 써야함!
    private String role; //Enum을 쓰는게 좋지만, 지금은 String사용.

    @CreationTimestamp //시간이 자동 입력됨.
    private Timestamp createDate;
}
