package com.sparta.hanghaememo.service;


import com.sparta.hanghaememo.dto.MemoRequestDto;
import com.sparta.hanghaememo.dto.MemoResponseDto;
import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.entity.User;
import com.sparta.hanghaememo.jwt.JwtUtil;
import com.sparta.hanghaememo.repository.MemoRepository;
import com.sparta.hanghaememo.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    @Transactional
    public Memo createMemo(MemoRequestDto requestDto, HttpServletRequest httpServletRequest) {
        String token = jwtUtil.resolveToken(httpServletRequest);
        String tempUserName = "";
        if(token != null){
            if(jwtUtil.validateToken(token)){
                Claims claims = jwtUtil.getUserInfoFromToken(token);
                tempUserName = claims.getSubject();
            }else{
                throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
            }
        }else{
            throw new IllegalArgumentException("토큰이 없습니다");
        }
        User user = userRepository.findByUsername(tempUserName).orElseThrow();


        Memo memo = new Memo(requestDto);
        memoRepository.save(memo);
        return memo;
    }

    @Transactional(readOnly = true)
    public List<MemoResponseDto> getMemos() {
        return memoRepository.findAllByOrderByModifiedAtDesc().stream().map(MemoResponseDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MemoResponseDto getMemos(long id) {
        Memo entity = memoRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 게시글은 존재하지 않습니다")
        );
        return new MemoResponseDto(entity);
    }

    @Transactional
    public MemoResponseDto update(Long id, MemoRequestDto requestDto, HttpServletRequest httpServletRequest) {
        String token = jwtUtil.resolveToken(httpServletRequest);
        String tempUserName = "";
        Memo memo = memoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );

        if(token != null){
            if(jwtUtil.validateToken(token)){
                Claims claims = jwtUtil.getUserInfoFromToken(token);
                tempUserName = claims.getSubject();
            }else{
                throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
            }
        }else{
            throw new IllegalArgumentException("토큰이 없습니다");
        }
        User user = userRepository.findByUsername(tempUserName).orElseThrow();

        memo.update(requestDto);
        return new MemoResponseDto(memo);
    }

    @Transactional
    public String deleteMemo(Long id, HttpServletRequest httpServletRequest) {
        String token = jwtUtil.resolveToken(httpServletRequest);
        String tempUserName = "";
        Memo memo = memoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        if(token != null){
            if(jwtUtil.validateToken(token)){
                Claims claims = jwtUtil.getUserInfoFromToken(token);
                tempUserName = claims.getSubject();
            }else{
                throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
            }
        }else{
            throw new IllegalArgumentException("토큰이 없습니다");
        }
        User user = userRepository.findByUsername(tempUserName).orElseThrow();

        memoRepository.deleteById(id);
        return "아이디 "+id +"의 내용을 모두 삭제했습니다";
    }

}
