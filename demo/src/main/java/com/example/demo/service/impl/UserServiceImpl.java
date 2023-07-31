package com.example.demo.service.impl;

import com.example.demo.dao.UserDAO;
import com.example.demo.dao.impl.UserDAOImpl;
import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.LoginResponse;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.exception.AuthenticationException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
  private final UserDAO userDAO = new UserDAOImpl();

  @Override
  public UserResponse create(UserRequest userRequest) {
    User user = new User(
          userRequest.getUsername(),
          userRequest.getPassword(),
          userRequest.getEmail());
    userDAO.create(user);
    UserResponse userResponse = new UserResponse(user.getId(), user.getUsername(), user.getEmail());
    return userResponse;
  }

  @Override
  public UserResponse update(UserRequest userRequest, int id) {
    User existingUser = userDAO.getById(id);
    if (existingUser != null) {
      existingUser.setUsername(userRequest.getUsername());
      existingUser.setPassword(userRequest.getPassword());
      existingUser.setEmail(userRequest.getEmail());
      User updatedUser = userDAO.update(existingUser, id);
      UserResponse userResponse = new UserResponse(
            updatedUser.getId(),
            updatedUser.getUsername(),
            updatedUser.getEmail());
      return userResponse;
    } else {
      throw new NotFoundException();
    }
  }

  @Override
  public void delete(int id) {
    userDAO.delete(id);
  }

  @Override
  public List<UserResponse> list() {
    List<User> users = userDAO.list();
    List<UserResponse> usersResponse = new ArrayList<>();
    int id;
    String username, email;
    UserResponse userResponse;
    for (User user : users) {
      id = user.getId();
      username = user.getUsername();
      email = user.getEmail();
      userResponse = new UserResponse(id, username, email);
      usersResponse.add(userResponse);
    }
    return usersResponse;
  }

  @Override
  public UserResponse getByUsername(String username) {
    User user = userDAO.getByUsername(username);
    UserResponse userResponse = new UserResponse(
          user.getId(),
          user.getUsername(),
          user.getEmail());
    return userResponse;
  }

  public LoginResponse login(LoginRequest loginRequest) {
    User user = new User(loginRequest.getUsername(), loginRequest.getPassword());
    User loggedInUser = userDAO.login(user);
    if (loggedInUser == null) {
      throw new AuthenticationException();
    } else {
      User getUser = userDAO.getByUsername(loginRequest.getUsername());
      return new LoginResponse(getUser.getId(), getUser.getUsername(), getUser.getEmail());
    }
  }

}
