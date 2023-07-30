package com.example.demo.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequest {
    @NotBlank(message = "username not blank")
    private String username;
    @NotBlank(message = " password not blank")
    @Min(value = 8, message = "must be more than 8 characters ")
    private String password;
    @Email(message = "Invalid email")
    private String email;
}
