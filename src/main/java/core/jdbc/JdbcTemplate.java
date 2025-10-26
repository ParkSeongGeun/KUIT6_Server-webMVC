package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate<T> {

    private final ConnectionProvider connectionProvider;

    public JdbcTemplate(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public void update(String sql, PreparedStatementSetter psSetter) {
        try(Connection conn = connectionProvider.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            psSetter.setParameters(ps);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public void update(String sql, PreparedStatementSetter psSetter, KeyHolder keyHolder) {
        try(Connection conn = connectionProvider.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {
            psSetter.setParameters(ps);
            ps.executeUpdate();

           try (ResultSet rs = ps.getGeneratedKeys()) {
               if (rs.next()) {
                   keyHolder.setId(rs.getLong(1));
               } else {
                   throw new IllegalStateException("Generated key not found");
               }
           }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public List<T> query(String sql, RowMapper<T> rowMapper) {
        List<T> objects = new ArrayList<T>();

        try(Connection conn = connectionProvider.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();) {
            while (rs.next()) {
                T obj = rowMapper.mapRow(rs);
                objects.add(obj);
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }

        return objects;
    }

    public T queryForObject(String sql, PreparedStatementSetter psSetter, RowMapper<T> rowMapper) {
        T obj = null;

        try(Connection conn = connectionProvider.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);) {
            psSetter.setParameters(ps);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rowMapper.mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }

        return obj;
    }
}
