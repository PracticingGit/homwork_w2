package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.repository.MemoRepository;
import com.sparta.hanghaememo.dto.CommentRequestDto;
import com.sparta.hanghaememo.dto.CommentResponseDto;
import com.sparta.hanghaememo.entity.Comment;
import com.sparta.hanghaememo.repository.CommentRepository;
import com.sparta.hanghaememo.dto.ExcepMsg;
import com.sparta.hanghaememo.jwt.JwtUtil;
import com.sparta.hanghaememo.entity.User;
import com.sparta.hanghaememo.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static com.sparta.hanghaememo.entity.UserRoleEnum.USER;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private  final JwtUtil jwtUtil;
    private final MemoRepository memoRepository;
    private final UserRepository userRepository;
    public CommentResponseDto create(Long memoId, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        if(token == null) {
            return null;
        }

        if(!jwtUtil.validateToken(token)) {
            return null;
        }
        Claims claims = jwtUtil.getUserInfoFromToken(token);
        Memo memo = memoRepository.findById(memoId).orElseThrow();
        Comment comment = new Comment(commentRequestDto, memo, claims.getSubject());

        commentRepository.save(comment);
        return CommentResponseDto.of(comment);
    }

    public List<Comment> getComment(Long id) {
        List<Comment> comments = commentRepository.findByMemo_IdOrderByCreateAtDesc(id);
        return comments.stream().filter(comment -> !comment.getIsDeleted()).collect(Collectors.toList());
    }

    @Transactional
    public CommentResponseDto update(Long commentId, CommentRequestDto commentRequestDto, HttpServletRequest request) throws  IllegalAccessException {
        String token = jwtUtil.resolveToken(request);
        Claims claims = jwtUtil.getUserInfoFromToken(token);
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow();
        if(!jwtUtil.validateToken(token)) {
            return null;
        }
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        if(!comment.getUserName().equals(user.getUserName()) && user.getRole() == USER) {
            throw new IllegalAccessException("작성자만 삭제/수정할 수 있습니다.");
        }
        comment.update(commentRequestDto.getContent());

        return new CommentResponseDto(comment);

    }

    @Transactional
    public ExcepMsg deleteComment(Long commentId, HttpServletRequest request) throws IllegalAccessException {
        String token = jwtUtil.resolveToken(request);
        Claims claims = jwtUtil.getUserInfoFromToken(token);
        User users = userRepository.findByUsername(claims.getSubject()).orElseThrow();
        if(!jwtUtil.validateToken(token)) {
            return null;
        }
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        if(!comment.getUserName().equals(users.getUserName()) && users.getRole() == USER) {
            throw new IllegalAccessException("작성자만 삭제/수정할 수 있습니다.");
        }
//        commentRepository.deleteById(cId);
        comment.setIsDeleted();
        return new ExcepMsg("삭제 성공", OK.value());
    }
}