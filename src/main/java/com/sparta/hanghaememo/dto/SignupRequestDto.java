package com.sparta.hanghaememo.dto;

import lombok.Getter;
import lombok.Setter;
//import org.intellij.lang.annotations.Pattern;

@Setter
@Getter


public class SignupRequestDto {

//    @Size(min = 4, max = 10)
//    @Pattern(regexp = "[0-9a-z]+]")
    private String username;

//    @Size(min = 8, max = 15)
//    @Pattern(regexp = "[0-9a-zA-Z!@#$%^&*(),.?\":{}|<>]+]")
    private String password;
//
//    private boolean admin = false;
//    private String adminToken = "";

}