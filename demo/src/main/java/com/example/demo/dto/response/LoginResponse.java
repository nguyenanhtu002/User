package com.example.demo.dto.response;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class LoginResponse {

    private int id;
    private String username;
    private String email;
    private String token;

    public LoginResponse(String username, String email) {
      this.username = username;
      this.email = email;
    }
  public LoginResponse(int id, String username, String email) {
    this.id = id;
    this.username = username;
    this.email = email;
  }
  }

