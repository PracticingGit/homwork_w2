package com.sparta.hanghaememo.repository;


import com.sparta.hanghaememo.entity.Comment;
import com.sparta.hanghaememo.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Memo> findAllByOrderByModifiedAtDesc();

    List<Comment> findByMemo_IdOrderByCreateAtDesc(Long id);//??
}