package com.example.demo.dao.impl;

import com.example.demo.dao.DataSource;
import com.example.demo.dao.UserDAO;
import com.example.demo.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDAOImpl implements UserDAO {
    private static final String CREATE_USER = "INSERT INTO users (username,password,email) VALUES (?,?,?)";
    private static final String UPDATE_USER = "UPDATE users SET username=?,password=?,email=? WHERE id=?";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
    private static final String GET_ALL = "SELECT id, username,password, email FROM Users";
    private static final String CHECK_USER = "SELECT * FROM users WHERE username = ?";
    private static final String GET_BY_USERNAME = "SELECT * FROM users WHERE username=?";

    @Override
    public User create(User user) {
        Connection conn = null;
        try {
            conn = DataSource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(CREATE_USER);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(conn)) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        return user;
    }

    @Override
    public User update(User user, int id) {
        Connection conn = null;
        try {
            conn = DataSource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(UPDATE_USER);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setInt(4, id);
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(conn)) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        return user;
    }

    @Override
    public void delete(int id) {
        Connection conn = null;
        try {
            conn = DataSource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(DELETE_USER);
            ps.setInt(1, id);
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(conn)) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    @Override
    public User getByUsername(String username) {
        Connection conn = null;
        try {
            conn = DataSource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(GET_BY_USERNAME);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                return user;
            }
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(conn)) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        List<User> list = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DataSource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(GET_ALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                list.add(user);
            }
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(conn)) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        return list;
    }

    public User login(User user) {
        Connection conn = null;
        try {
            conn = DataSource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(CHECK_USER);
            ps.setString(1, user.getUsername());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String dbUsername = rs.getString("username");
                String dbPassword = rs.getString("password");
                String email = rs.getString("email");

                if (user.getPassword().equals(dbPassword)) {
                    user.setId(id);
                    user.setUsername(dbUsername);
                    user.setEmail(email);
                } else {
                    user = null;
                }
            } else {
                user = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return user;
    }
}