package com.sparta.hanghaememo.entity;

import com.sparta.hanghaememo.dto.SignupRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.sparta.hanghaememo.entity.UserRoleEnum.USER;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @Column(name = "User_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String userName;
    @Column(nullable = false)
    private String password;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private UserRoleEnum role;
    public User(SignupRequestDto requestDto) {
        this.userName = requestDto.getUsername();
        this.password = requestDto.getPassword();
        this.role = USER;
    }

}