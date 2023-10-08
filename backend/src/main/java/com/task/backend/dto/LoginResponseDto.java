package com.task.backend.dto;



import java.io.Serializable;

public class LoginResponseDto implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String token;

    public LoginResponseDto(String token) {
        this.token = token;
    }
}
