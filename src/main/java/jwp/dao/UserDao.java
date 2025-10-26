package jwp.dao;

import core.jdbc.ConnectionManager;
import core.jdbc.ConnectionProvider;
import core.jdbc.JdbcTemplate;
import core.jdbc.PreparedStatementSetter;
import core.jdbc.RowMapper;
import jwp.model.User;

import java.sql.SQLException;
import java.util.List;

public class UserDao {

    private final JdbcTemplate<User> jdbcTemplate;

    public UserDao(ConnectionProvider connectionProvider) {
        this.jdbcTemplate = new JdbcTemplate<>(connectionProvider);
    }

    public void insert(User user) throws SQLException {
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        PreparedStatementSetter setter = ps -> {
            ps.setString(1, user.getUserId());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setString(4, user.getEmail());
        };
        jdbcTemplate.update(sql, setter);
    }

    public void update(User user) throws SQLException {
        String sql = "UPDATE USERS SET PASSWORD = ?, NAME = ?, EMAIL = ? WHERE USERID = ?";
        PreparedStatementSetter psSetter = ps -> {
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getUserId());
        };
        jdbcTemplate.update(sql, psSetter);
    }

    public void delete(User user) throws SQLException {
        String sql = "DELETE FROM USERS WHERE USERID = ?";
        PreparedStatementSetter psSetter = ps -> {
            ps.setString(1, user.getUserId());
        };
        jdbcTemplate.update(sql, psSetter);
    }

    // TODO findAll, findByUserId
    public List<User> findAll() throws SQLException {
        String sql = "SELECT * FROM USERS";
        RowMapper<User> rowMapper = rs -> new User(
                rs.getString("userId"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("email")
        );
        return jdbcTemplate.query(sql, rowMapper);
    }

    public User findUserById(String userId) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE USERID = ?";

        PreparedStatementSetter psSetter = ps -> {
            ps.setString(1, userId);
        };

        RowMapper<User> rowMapper = rs -> new User(
                rs.getString("userId"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("email")
        );

        return jdbcTemplate.queryForObject(sql, psSetter, rowMapper);
    }
}
