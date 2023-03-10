package com.sparta.hanghaememo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;
//import org.intellij.lang.annotations.Pattern;

@Setter
@Getter


public class SignupRequestDto {

    @Size(min = 4, max = 10)
    @Pattern(regexp = "[0-9a-z]+]")
    private String username;

    @Size(min = 8, max = 15)
    @Pattern(regexp = "[0-9a-zA-Z!@#$%^&*(),.?\":{}|<>]+]")
    private String password;
    private String adminToken;
    public SignupRequestDto(String username, String password){
        this.username = username;
        this.password = password;
    }

//    public boolean isAdmin() {
//
//    }

}