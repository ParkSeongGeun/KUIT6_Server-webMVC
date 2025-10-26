package core.jdbc;

import jwp.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate<T> {

    public void update(String sql, PreparedStatementSetter psSetter) throws SQLException {
        try(Connection conn = ConnectionManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            psSetter.setParameters(ps);
            ps.executeUpdate();
        }
    }

    public List<T> query(String sql, RowMapper<T> rowMapper) throws SQLException {
        List<T> objects = new ArrayList<T>();

        try(Connection conn = ConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();) {
            while (rs.next()) {
                T obj = rowMapper.mapRow(rs);
                objects.add(obj);
            }
        }

        return objects;
    }

    public T queryForObject(String sql, PreparedStatementSetter psSetter, RowMapper<T> rowMapper) throws SQLException {
        ResultSet rs = null;
        T obj = null;

        try(Connection conn = ConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);) {
            psSetter.setParameters(ps);
            rs = ps.executeQuery();
            if (rs.next()) {
                obj = rowMapper.mapRow(rs);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
        }

        return obj;
    }
}
