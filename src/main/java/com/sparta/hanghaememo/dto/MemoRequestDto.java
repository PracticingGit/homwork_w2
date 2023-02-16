package com.sparta.hanghaememo.dto;

import lombok.Getter;

@Getter
public class MemoRequestDto {
    private String username;
    private String contents;
    private String title;
//    private String password;

//    public boolean CheckingPassword(String password){
//        if(this.password.equals(password)){
//            return true;
//        }else{
//            return false;
//        }
//    }
}