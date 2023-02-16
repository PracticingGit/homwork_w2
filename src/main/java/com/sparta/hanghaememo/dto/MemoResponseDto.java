package com.sparta.hanghaememo.dto;

import com.sparta.hanghaememo.entity.Memo;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
public class MemoResponseDto {
    private String username;
    private String contents;
    private String title;
    private LocalDateTime createdAt;

    public MemoResponseDto(Memo entity) {
        this.username = entity.getUsername();
        this.contents = entity.getContents();
        this.title = entity.getTitle();
        this.createdAt = entity.getCreatedAt();
    }
}