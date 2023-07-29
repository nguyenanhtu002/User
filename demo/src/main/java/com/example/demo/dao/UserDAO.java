package com.example.demo.dao;

import com.example.demo.entity.User;

import java.util.List;

public interface UserDAO {
    User create(User user);
    User update(User user, int id);
    void delete(int id);
    User getByUsername(String username);
    List<User> getAll();
    User login(User user);
}
