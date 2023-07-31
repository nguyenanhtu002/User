package com.example.demo.controller;

import com.example.demo.constant.MessageResponse;
import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.LoginResponse;
import com.example.demo.dto.response.ResponseGeneral;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.service.UserService;
import com.example.demo.util.TokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("")
  public ResponseGeneral<UserResponse> create(
        @RequestBody @Valid UserRequest userRequest) {
    UserResponse userResponse = userService.create(userRequest);
    return new ResponseGeneral<>(MessageResponse.CREATE_USER, userResponse, HttpStatus.CREATED.value());
  }

  @PutMapping("/id")
  public ResponseGeneral<UserResponse> update(
        @RequestBody @Valid UserRequest userRequest, @PathVariable(name = "id") int id) {
    UserResponse user = userService.update(userRequest, id);
    return new ResponseGeneral<>(MessageResponse.UPDATE_SUCCESS, user, HttpStatus.OK.value());
  }


  @GetMapping("/")
  public ResponseGeneral<List<UserResponse>> list() {
    List<UserResponse> users = userService.list();
    return new ResponseGeneral<>(MessageResponse.GET_USER, users, HttpStatus.OK.value());
  }

  @DeleteMapping("/id")
  public ResponseGeneral<Void> delete(@PathVariable(name = "id") int id) {
    userService.delete(id);
    return new ResponseGeneral<>(MessageResponse.DELETE_USER, HttpStatus.OK.value());
  }

  @PostMapping("/login")
  public ResponseGeneral<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
    LoginResponse user = userService.login(loginRequest);
    String token = TokenUtil.generateToken();
    user.setToken(token);
    return new ResponseGeneral<>(MessageResponse.LOGIN_SUCCESS, user, HttpStatus.OK.value());
  }
}
