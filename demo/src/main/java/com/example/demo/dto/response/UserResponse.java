package com.example.demo.dto.response;

import com.example.demo.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
    private int id;
    private String username;
    private String email;

    public UserResponse(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
    }

    public UserResponse(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public UserResponse(int id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
