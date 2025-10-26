package core.jdbc;

import jwp.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {

    public void update(String sql, PreparedStatementSetter psSetter) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConnectionManager.getConnection();
            ps = conn.prepareStatement(sql);
            psSetter.setParameters(ps);
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

    public List<User> query(String sql, RowMapper rowMapper) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<User>();

        try {
            conn = ConnectionManager.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = rowMapper.mapRow(rs);
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

    public User queryForObject(String sql, PreparedStatementSetter psSetter, RowMapper rowMapper) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;

        try {
            conn = ConnectionManager.getConnection();
            ps = conn.prepareStatement(sql);
            psSetter.setParameters(ps);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = rowMapper.mapRow(rs);
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
