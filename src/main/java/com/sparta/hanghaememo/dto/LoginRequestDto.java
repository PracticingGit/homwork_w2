package com.sparta.hanghaememo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class LoginRequestDto {
    private String username;
    private String password;
}