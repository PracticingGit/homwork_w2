package com.sparta.hanghaememo.controller;

import com.sparta.hanghaememo.dto.CommentRequestDto;
import com.sparta.hanghaememo.dto.CommentResponseDto;
import com.sparta.hanghaememo.service.CommentService;
import com.sparta.hanghaememo.dto.ExcepMsg;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/board/{id_memo}/comment")
    public ResponseEntity<Object> createComment(@PathVariable(name = "id_memo") Long memoId, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        CommentResponseDto commentResponseDto = null;
        try {
            commentResponseDto = commentService.create(memoId, commentRequestDto, request);
            if(commentResponseDto == null) {
                return new ResponseEntity<>(new ExcepMsg("토큰이 유효하지 않습니다.", BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(commentResponseDto, OK);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ExcepMsg("토큰이 유효하지 않습니다.", BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }


    }

    @PatchMapping("/board/{id_memo}/comment/{id_comment}")
    public ResponseEntity<Object> update(@PathVariable(name = "id_comment") Long commentId, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        CommentResponseDto commentResponseDto = null;

        try {
            commentResponseDto = commentService.update(commentId, commentRequestDto, request);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(new ExcepMsg("작성자만 삭제/수정할 수 있습니다.", BAD_REQUEST.value()), BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ExcepMsg("토큰이 유효하지 않습니다.", BAD_REQUEST.value()), BAD_REQUEST);
        }
        return new ResponseEntity<>(commentResponseDto, OK);
    }

    @DeleteMapping("/board/{id_memo}/comment/{id_comment}")
    public ResponseEntity<Object> delete(@PathVariable(name = "id_comment") Long cId, HttpServletRequest request) {
        ExcepMsg msg = null;
        try {
            msg = commentService.deleteComment(cId, request);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(new ExcepMsg("작성자만 삭제/수정할 수 있습니다.", BAD_REQUEST.value()), BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ExcepMsg("토큰이 유효하지 않습니다.", BAD_REQUEST.value()), BAD_REQUEST);
        }
        return new ResponseEntity<>(msg, OK);
    }


}