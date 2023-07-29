package com.example.demo.service;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse create(UserRequest userRequest);
    UserResponse update(UserRequest userRequest, int id);
    void delete(int id);
    List<UserResponse> getAll();
    UserResponse getByUsername(String username);
    UserResponse login(LoginRequest loginRequest);
}
