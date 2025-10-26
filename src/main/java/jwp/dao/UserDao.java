package jwp.dao;

import core.jdbc.ConnectionManager;
import jwp.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    public void insert(User user) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConnectionManager.getConnection();
            String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUserId());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setString(4, user.getEmail());
            ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public void update(User user) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConnectionManager.getConnection();
            String sql = "UPDATE USERS SET PASSWORD = ? NAME = ?, EMAIL = ? WHERE USER_ID = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getUserId());
            ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public void delete(User user) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConnectionManager.getConnection();
            String sql = "DELETE FROM USERS WHERE USER_ID = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUserId());
            ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    // TODO findAll, findByUserId
    public List<User> findAll() throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<User>();

        try {
            conn = ConnectionManager.getConnection();
            String sql = "SELECT * FROM USERS";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User(
                        rs.getString("USER_ID"),
                        rs.getString("PASSWORD"),
                        rs.getString("NAME"),
                        rs.getString("EMAIL")
                );
                users.add(user);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return users;
    }

    public User findUserById(String userId) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;

        try {
            conn = ConnectionManager.getConnection();
            String sql = "SELECT * FROM USERS WHERE USER_ID = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = new User(
                        rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email")
                );
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return user;
    }
}
