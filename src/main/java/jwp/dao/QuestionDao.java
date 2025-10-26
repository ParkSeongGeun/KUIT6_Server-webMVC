package jwp.dao;

import core.jdbc.ConnectionProvider;
import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;
import jwp.model.Question;
import jwp.model.User;
import java.util.List;

public class QuestionDao {

    private final JdbcTemplate<Question> jdbcTemplate;

    public QuestionDao(ConnectionProvider connectionProvider) {
        this.jdbcTemplate = new JdbcTemplate<>(connectionProvider);
    }

    public List<Question> findAll() {
        String sql = "SELECT * FROM QUESTIONS";
        RowMapper<Question> rowMapper = rs -> new Question(
                rs.getLong("questionId"),
                rs.getString("writer"),
                rs.getString("title"),
                rs.getString("contents"),
                rs.getTimestamp("createdDate").toLocalDateTime(),
                rs.getInt("countOfAnswer")
        );
        return jdbcTemplate.query(sql, rowMapper);
    }
}
