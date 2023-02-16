package com.sparta.hanghaememo.controller;


import com.sparta.hanghaememo.dto.MemoRequestDto;
import com.sparta.hanghaememo.dto.MemoResponseDto;
import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("index");
    }

//    신규등록하기
    @PostMapping("/api/memos")
    public Memo createMemo(@RequestBody MemoRequestDto requestDto, HttpServletRequest httpServletRequest) {
        return memoService.createMemo(requestDto, httpServletRequest);
    }

//    전체 메모 가져오기
    @GetMapping("/api/memos")
    public List<MemoResponseDto> getMemos() {
        return memoService.getMemos();
    }

//    특정 메모 가져오기
    @GetMapping("/api/memos/{id}")
    public MemoResponseDto getMemos(@PathVariable Long id) {
        return memoService.getMemos(id);
    }

    @PutMapping("/api/memos/{id}/{password}")
    public MemoResponseDto updateMemo(@PathVariable Long id, @PathVariable String password, @RequestBody MemoRequestDto requestDto) {
        HttpServletRequest httpServletRequest;
        return memoService.update(id, requestDto, httpServletRequest);
    }

    @DeleteMapping("/api/memos/{id}/{password}")
    public String deleteMemo(@PathVariable Long id,@PathVariable String password) {
        HttpServletRequest httpServletRequest;
        return memoService.deleteMemo(id, httpServletRequest);
    }


}