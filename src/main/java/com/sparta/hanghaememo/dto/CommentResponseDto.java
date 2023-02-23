package com.sparta.hanghaememo.dto;

import com.sparta.hanghaememo.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {

    private String content;
    private String userName;

    private LocalDateTime createAt;

    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment entity) {
        this.content = entity.getContent();
        this.userName = entity.getUserName();
        this.createAt = entity.getCreatedAt();
        this.modifiedAt = entity.getModifiedAt();
    }


    public static CommentResponseDto of(Comment comment) {
        return CommentResponseDto.builder()
                .content(comment.getContent())
                .userName(comment.getUserName())
                .createAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }
}