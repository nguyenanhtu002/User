package com.example.demo.controller;

import com.example.demo.constant.MessageResponse;
import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.ResponseGeneral;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@Validated
@RequestMapping("api/v1/users")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/create")
  public ResponseGeneral<UserResponse> create(
        @RequestBody @Valid UserRequest userRequest) {
    return new ResponseGeneral<>(MessageResponse.CREATE_USER, userService.create(userRequest));
  }

  @PutMapping("update/{id}")
  public ResponseGeneral<UserResponse> update(
        @RequestBody @Valid UserRequest userRequest, @PathVariable(name = "id") int id) {
    return new ResponseGeneral<>(MessageResponse.UPDATE_SUCCESS, userService.update(userRequest, id));
  }

  @GetMapping("/get")
  public ResponseGeneral<List<UserResponse>> get() {
    List<UserResponse> users = userService.getAll();
    return new ResponseGeneral<>(MessageResponse.GET_USER, users);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseGeneral<Void> delete(@PathVariable(name = "id") int id) {
    userService.delete(id);
    return new ResponseGeneral<>(MessageResponse.DELETE_USER);
  }

  @PostMapping("/login")
  public ResponseGeneral<UserResponse> login(@RequestBody LoginRequest loginRequest) {
    UserResponse user = userService.login(loginRequest);
    if (user != null) {
      return new ResponseGeneral<>(MessageResponse.LOGIN_SUCCESS, user);
    } else {
      return new ResponseGeneral<>(MessageResponse.LOGIN_FAIL);
    }
  }
}
