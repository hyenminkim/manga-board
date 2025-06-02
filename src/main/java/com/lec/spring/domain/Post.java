package com.lec.spring.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Model 객체 (domain)

/**
 * DTO 객체
 *  : Data Transfer Object 라고도 함.
 *
 *  객체 -> DB
 *  DB -> 객체
 *  request -> 객체
 *  객체 -> response
 *  ..
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
@Entity(name = "t7_post")
@DynamicInsert   // insert 시 null 인 필드 제외
@DynamicUpdate  // update 시 null 인 필드 제외
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_seq")
    @SequenceGenerator(name = "post_seq", sequenceName = "POST_SEQ", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String subject;
    @Column(columnDefinition = "CLOB")
    private String content;
    @ColumnDefault(value = "0")
    private long viewCnt;


    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime regDate;

    // Post:User = N:1
    @ManyToOne(optional = false)
    // optional = false : user_id bigint not null
    //  이게 없으면 Post 의 find..동작시  Post 와 User 사이에 left outer join 발생
    @ToString.Exclude
    private Member member;   // 글 작성자 (FK)

    // 첨부파일
    // Post:File = 1:N
    @OneToMany(cascade = CascadeType.ALL) // cascade = CascadeType.ALL : 삭제등의 동작 발생시 child 도 함께 삭제
    @JoinColumn(name = "post_id")   // 중간테이블 없애고 post_id 로 join 수행
    @ToString.Exclude
    @Builder.Default
    private List<Attachment> fileList = new ArrayList<>();

    public void addFiles(Attachment... files) {
        Collections.addAll(fileList, files);
    }

    // Post:Comment = 1:N
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    @ToString.Exclude
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    public void addComments(Comment... comments){
        Collections.addAll(this.comments, comments);
    }

}









