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
import com.example.demo.util.TokenUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService {
  private final UserDAO userDAO = new UserDAOImpl();
  private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

  @Override
  public UserResponse create(UserRequest userRequest) {
    logger.log(Level.INFO, "Creating a new user: {0}", userRequest.getUsername());

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
    logger.log(Level.INFO, "Updating user with ID: {0}", id);
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
      logger.log(Level.WARNING, "User not found with ID: {0}", id);
      throw new NotFoundException();
    }
  }

  @Override
  public void delete(int id) {
    logger.log(Level.INFO, "Deleting user with ID: {0}", id);
    userDAO.delete(id);
  }

  @Override
  public List<UserResponse> list() {
    logger.log(Level.INFO, "Retrieving all users");
    List<User> users = userDAO.list();
    List<UserResponse> usersResponse = new ArrayList<>();
    UserResponse userResponse;
    for (User user : users) {
      userResponse = new UserResponse(user.getId(), user.getUsername(), user.getUsername());
      usersResponse.add(userResponse);
    }
    return usersResponse;
  }

  @Override
  public UserResponse getByUsername(String username) {
    logger.log(Level.INFO, "Retrieving user with username: {0}", username);
    User user = userDAO.getByUsername(username);
    UserResponse userResponse = new UserResponse(
          user.getId(),
          user.getUsername(),
          user.getEmail());
    return userResponse;
  }

  public LoginResponse login(LoginRequest loginRequest) {
    logger.log(Level.INFO, "Processing login request for user: {0}", loginRequest.getUsername());
    User user = new User(loginRequest.getUsername(), loginRequest.getPassword());
    User loggedInUser = userDAO.login(user);
    if (loggedInUser == null) {
      logger.log(Level.WARNING, "Authentication failed for user: {0}", loginRequest.getUsername());
      throw new AuthenticationException();
    } else {
      String token = TokenUtil.generateToken();
      user.setToken(token);
      User getUser = userDAO.getByUsername(loginRequest.getUsername());
      logger.log(Level.INFO, "User logged in successfully: {0}", getUser.getUsername());
      return new LoginResponse(getUser.getId(), getUser.getUsername(), getUser.getEmail(), token);
    }
  }
}
