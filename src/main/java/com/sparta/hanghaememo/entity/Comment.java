package com.sparta.hanghaememo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparta.hanghaememo.entity.Timestamped;
import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id @Column(name = "COMMENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    @Column(nullable = false)
    private String content;

    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "MEMO_ID")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Memo memo;

    public Comment(CommentRequestDto commentRequestDto, Memo memo, String userName) {
        this.userName = userName;
        this.content = commentRequestDto.getContent();
        this.memo = memo;
        this.isDeleted = false;
    }


    public Comment update(String content) {
        this.content = content;
        return this;
    }

    public void setIsDeleted() {
        this.isDeleted = true;
    }
}