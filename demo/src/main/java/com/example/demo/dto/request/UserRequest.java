package com.example.demo.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

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
